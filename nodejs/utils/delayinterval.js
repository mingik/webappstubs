function Bomb() {
  this.message = "BOOM!";
}

Bomb.prototype.explode = function() {
  console.log(this.message);
};

var bomb = new Bomb();

setTimeout(bomb.explode.bind(bomb), 1000); // execute a function after timeout

var timeoutId = setTimeout(bomb.explode.bind(bomb), 2000);

clearTimeout(timeoutId); // clear second one
console.log('Second bomb was cleared!');

//

function tick() {
  console.log('tick:', Date.now());
}

function tock() {
  console.log('tock:', Date.now());
}

var tickId = setInterval(tick, 1000);
//id.unref();

setTimeout(function() {
  setInterval(tock, 1000);
}, 500);