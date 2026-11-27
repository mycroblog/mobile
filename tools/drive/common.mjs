// What both platforms of the app-driving tool share.

export class StepError extends Error {
  constructor(message, reply) {
    super(message);
    this.reply = reply;
  }
}

export function need(value, what, step) {
  if (value === undefined || value === true || value === '') {
    throw new StepError(`${step} needs ${what}: scripts/drive <os> ${step} --help`);
  }
  return value;
}

export function oneOf(value, allowed, step) {
  if (!allowed.includes(value)) {
    throw new StepError(`${step} takes one of ${allowed.join(', ')}, not "${value ?? ''}"`);
  }
  return value;
}

export function target(positional, flags, step, { labelFlag = null, idFlag = 'id', atFlag = 'at' } = {}) {
  if (flags[idFlag] && flags[idFlag] !== true) return { id: flags[idFlag] };
  if (atFlag && flags[atFlag] && flags[atFlag] !== true) {
    const m = /^(\d+(?:\.\d+)?),(\d+(?:\.\d+)?)$/.exec(flags[atFlag]);
    if (!m) throw new StepError(`--at takes <x>,<y>, not "${flags[atFlag]}"`);
    return { x: Number(m[1]), y: Number(m[2]) };
  }
  const label = labelFlag ? flags[labelFlag] : positional[0];
  if (label && label !== true) return { label };
  return null;
}

export function parseSize(value, step) {
  const m = /^(\d+)x(\d+)$/.exec(value ?? '');
  if (!m) throw new StepError(`${step} takes <width>x<height> or reset, not "${value ?? ''}"`);
  return { width: Number(m[1]), height: Number(m[2]) };
}

export function parseData(flags) {
  const data = {};
  for (const kv of [].concat(flags.data ?? [])) {
    const i = String(kv).indexOf('=');
    if (i < 1) throw new StepError(`--data takes <key>=<value>, not "${kv}"`);
    data[kv.slice(0, i)] = kv.slice(i + 1);
  }
  return data;
}

// The runner of a started session, with the app every step acts on.
export function runnerOf(deps, name) {
  const state = () => {
    const s = deps.readState(name);
    if (!s) throw new StepError(`no ${name} session: run "scripts/drive ${name} start" first`);
    return s;
  };
  return {
    state,
    app() {
      const s = state();
      if (!s.app) throw new StepError(`no app yet: run "scripts/drive ${name} launch <app id>" first`);
      return s.app;
    },
    async call(op, args = {}, { withApp = true } = {}) {
      const s = state();
      const request = { op, ...args };
      if (withApp && s.app) request.app = s.app;
      const reply = await deps.send(s.port, request);
      if (!reply.ok) throw new StepError(reply.error ?? `${op} failed`, reply);
      return reply;
    },
  };
}
