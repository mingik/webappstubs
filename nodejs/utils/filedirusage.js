var fs = require('fs');
var assert = require('assert');

// directories

fs.readdir('.', function(err, files){
  console.log(files);
});

// IO with buffers

var fd = fs.openSync('./data.txt', 'w+');
var writeBuf = new Buffer('some additional data to write');
fs.writeSync(fd, writeBuf, 0, writeBuf.length, 0);

var readBuf = new Buffer(writeBuf.length);
fs.readSync(fd, readBuf, 0, writeBuf.length, 0);
assert.equal(writeBuf.toString(), readBuf.toString());

// IO with streams

var readable = fs.createReadStream('./data.txt');
var writable = fs.createWriteStream('./datacopy.txt');
readable.pipe(writable);

// IO with Files
fs.readFile('./data.txt', function(err, buf){
  if (err) throw err;
  console.log(buf);
});
