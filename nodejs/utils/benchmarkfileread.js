var args = {
  '-h': displayHelp,
  '-r': readFile
};

funciton displayHelp() {
  console.log('Argument processor:', args);

function readFile(file) {
  if (file && file.length) {
    console.log('Reading: ', file);
    console.time('read'); // start
    var stream = require('fs').createReadStream(file)
    stream.on('end', function() {
      console.timeEnd('read'); // end
    });
    stream.pipe(process.stdout);
  } else {
    console.error('A file must be provided with -r option');
    process.exit(1);
  }
}

if (process.argv.length > 0) {
  process.argv.forEach(function(arg, index) {
    args[arg].apply(this, process.argv.slice(index+1));
  });
}