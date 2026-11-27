// The client side of the runner protocol (tools/drive/README.md): one JSON object per line each way over local TCP.
import net from 'node:net';

export async function send(port, request, timeoutMs = 60_000) {
  return new Promise((resolve, reject) => {
    const socket = net.createConnection({ host: '127.0.0.1', port });
    let buffer = '';
    const timer = setTimeout(() => {
      socket.destroy();
      reject(new Error(`the runner on port ${port} did not answer "${request.op}" in ${timeoutMs / 1000} s`));
    }, timeoutMs);
    socket.setEncoding('utf8');
    socket.on('connect', () => socket.write(JSON.stringify(request) + '\n'));
    socket.on('data', (chunk) => {
      buffer += chunk;
      const nl = buffer.indexOf('\n');
      if (nl < 0) return;
      clearTimeout(timer);
      socket.end();
      try {
        resolve(JSON.parse(buffer.slice(0, nl)));
      } catch (e) {
        reject(new Error(`the runner answered something that is not JSON: ${e.message}`));
      }
    });
    socket.on('error', (e) => {
      clearTimeout(timer);
      reject(new Error(`no runner on port ${port} (${e.code ?? e.message}): run "scripts/drive <os> start" first`));
    });
  });
}

export async function waitForRunner(port, isAlive, timeoutMs = 180_000) {
  const deadline = Date.now() + timeoutMs;
  while (Date.now() < deadline) {
    try {
      const reply = await send(port, { op: 'ping' }, 5_000);
      if (reply.ok) return;
    } catch {
      // not up yet
    }
    if (!isAlive()) throw new Error('the runner exited while starting');
    await new Promise((r) => setTimeout(r, 500));
  }
  throw new Error(`the runner did not answer on port ${port} in ${timeoutMs / 1000} s`);
}
