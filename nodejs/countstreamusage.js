var CountStream = require('./utils/countstream');
var countStream = new CountStream('book');
var http = require('http');

http.get('http://www.oreilly.com', function(res){
  res.pipe(countStream);
});

countStream.on('total', function(count){
  console.log('Total matches:', count);
});