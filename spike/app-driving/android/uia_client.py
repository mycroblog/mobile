#!/usr/bin/env python3
"""Client of the UI Automator server in spike/Spike15Android/driver.

  uia_client.py start            # install-free: starts the server with am instrument and forwards tcp:9008
  uia_client.py step <command>   # e.g. "tap 540 1400", "tapText Row 3", "type hello", "back"; writes tree.xml, shot.png
  uia_client.py bench <n> <command>
"""
import socket, struct, subprocess, sys, time, os

PORT = 9008
OUT = os.environ.get("OUT", ".")


def start():
    t0 = time.time()
    subprocess.run(["adb", "forward", f"tcp:{PORT}", f"tcp:{PORT}"], check=True, capture_output=True)
    subprocess.Popen(["adb", "shell", "am", "instrument", "-w", "-e", "class", "dev.mycroblog.spike15.driver.Server",
                      "dev.mycroblog.spike15.driver.test/androidx.test.runner.AndroidJUnitRunner"],
                     stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, start_new_session=True)
    while True:
        try:
            s = connect()
            send(s, "none")
            break
        except (OSError, EOFError):
            time.sleep(0.1)
    print(f"server ready in {time.time() - t0:.2f}s")


def connect():
    s = socket.create_connection(("127.0.0.1", PORT))
    s.setsockopt(socket.IPPROTO_TCP, socket.TCP_NODELAY, 1)
    return s


def read_exact(s, n):
    buf = bytearray()
    while len(buf) < n:
        chunk = s.recv(n - len(buf))
        if not chunk:
            raise EOFError
        buf += chunk
    return bytes(buf)


def send(s, line):
    s.sendall((line + "\n").encode())
    frames = []
    first = read_exact(s, struct.unpack(">i", read_exact(s, 4))[0])
    frames.append(first)
    if line.split(" ")[0] not in ("tree", "shot") and not first.startswith(b"error"):
        frames.append(read_exact(s, struct.unpack(">i", read_exact(s, 4))[0]))
    return frames


def step(s, line):
    frames = send(s, line)
    if frames[0].startswith(b"error"):
        sys.exit(frames[0].decode())
    open(os.path.join(OUT, "tree.xml"), "wb").write(frames[0])
    if len(frames) > 1:
        open(os.path.join(OUT, "shot.png"), "wb").write(frames[1])


if __name__ == "__main__":
    cmd = sys.argv[1]
    if cmd == "start":
        start()
    elif cmd == "step":
        step(connect(), " ".join(sys.argv[2:]))
    elif cmd == "bench":
        n, line = int(sys.argv[2]), " ".join(sys.argv[3:])
        times = []
        for _ in range(n):
            t = time.time()
            step(connect(), line)
            times.append(time.time() - t)
            step(connect(), "back")
        times.sort()
        print("times", [round(x, 3) for x in times], "median", round(times[len(times) // 2], 3))
