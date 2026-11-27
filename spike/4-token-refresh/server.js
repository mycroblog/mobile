// Fake token server for spike #4: rotates refresh tokens on every use and
// revokes the whole family when a used refresh token is presented again,
// like my's /oauth/token. Run: node server.js  (listens on 127.0.0.1:8765)
const http = require('http');
const crypto = require('crypto');

let delayMs = 300;          // latency of every refresh, to widen race windows
const tokens = new Map();   // refresh token -> { family, gen, used }
const revoked = new Set();  // revoked families
let stats = { ok: 0, reuse: 0, afterRevoke: 0 };

const ts = () => new Date().toISOString().slice(11, 23);
const log = (s) => console.log(`${ts()} ${s}`);
const rnd = () => crypto.randomBytes(4).toString('hex');

function issue(family, gen) {
  const rt = `rt_${family}_${gen}_${rnd()}`;
  tokens.set(rt, { family, gen, used: false });
  return { access_token: `at_${family}_${gen}_${rnd()}`, refresh_token: rt, gen };
}

const server = http.createServer((req, res) => {
  let body = '';
  req.on('data', (c) => (body += c));
  req.on('end', () => {
    const url = new URL(req.url, 'http://x');
    const send = (code, obj) => { res.writeHead(code, { 'content-type': 'application/json' }); res.end(JSON.stringify(obj)); };
    if (url.pathname === '/issue') {
      const family = rnd();
      stats = { ok: 0, reuse: 0, afterRevoke: 0 };
      log(`ISSUE family=${family}`);
      return send(200, issue(family, 0));
    }
    if (url.pathname === '/config') {
      delayMs = Number(url.searchParams.get('delay') ?? delayMs);
      log(`CONFIG delay=${delayMs}ms`);
      return send(200, { delayMs });
    }
    if (url.pathname === '/stats') return send(200, { ...stats, revokedFamilies: [...revoked] });
    if (url.pathname === '/token') {
      const rt = new URLSearchParams(body).get('refresh_token');
      const who = req.headers['x-who'] || '?';
      const t = tokens.get(rt);
      // Rotation happens when the request arrives; the response is sent delayMs later.
      let result;
      if (!t) {
        result = [400, { error: 'invalid_grant', reason: 'unknown' }];
        log(`fail ${who} unknown token`);
      } else if (revoked.has(t.family)) {
        stats.afterRevoke++;
        result = [400, { error: 'invalid_grant', reason: 'family_revoked' }];
        log(`fail ${who} gen=${t.gen} family already revoked`);
      } else if (t.used) {
        revoked.add(t.family); stats.reuse++;
        result = [400, { error: 'invalid_grant', reason: 'reuse_revoked_family' }];
        log(`!!! REUSE by ${who} of gen=${t.gen} -> family ${t.family} REVOKED`);
      } else {
        t.used = true; stats.ok++;
        const n = issue(t.family, t.gen + 1);
        log(`ok   ${who} gen ${t.gen} -> ${n.gen}`);
        result = [200, n];
      }
      return setTimeout(() => send(...result), delayMs);
    }
    send(404, {});
  });
});
server.listen(8765, '127.0.0.1', () => log('listening on 127.0.0.1:8765'));
