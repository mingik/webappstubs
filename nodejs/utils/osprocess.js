/**
 * Run with $ cat file.txt | node osprocess.js
 */
process.stdin.resume();
process.stdin.setEncoding('utf8');

process.stdin.on('data', function(text) {
  process.stdout.write(text.toUpperCase());
});