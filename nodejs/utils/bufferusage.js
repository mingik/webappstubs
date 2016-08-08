var fs = require('fs');
fs.readFile('./data.txt', function(err, buf){
  console.log(buf);
  console.log(buf.toString()); // uses UTF-8
  console.log(buf.toString('ascii')); // uses ASCII
  console.log(buf.toString('base64')); // uses base64
});

// Read image as bytes
var mime = 'image/png';
var encoding = 'base64';
var data = fs.readFileSync('./ballmer_peak.png').toString(encoding);
var uri = 'data:' + mime + ';' + encoding + ',' + data;

// Write bytes to image
var data = uri.split(',')[1];
var buf = Buffer(data, 'base64');
fs.writeFileSync('./ballmer_peak_copy.png', buf);