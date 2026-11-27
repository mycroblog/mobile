// Renders a runner's tree (tools/drive/README.md) as one line per element, indented by depth.

export function renderTree(node, depth = 0, lines = []) {
  if (!node) return '';
  const bits = [node.role ?? '?'];
  if (node.label) bits.push(JSON.stringify(node.label));
  if (node.id) bits.push(`[${node.id}]`);
  if (node.value) bits.push(`=${JSON.stringify(node.value)}`);
  for (const s of node.states ?? []) bits.push(s);
  if (node.frame) bits.push(`@${node.frame.map((n) => Math.round(n)).join(',')}`);
  lines.push('  '.repeat(depth) + bits.join(' '));
  for (const c of node.children ?? []) renderTree(c, depth + 1, lines);
  return depth === 0 ? lines.join('\n') : undefined;
}
