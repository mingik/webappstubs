//var db = require('./db');

module.exports.index = function(req, res, next){
  var responseJS = {"result":"index"};
  res.send(responseJS);
  // db.notes.findAll(function(err, notes){
  //   if (err) return next(err);
  //   res.send(notes);
  // });
};

module.exports.create = function(req, res, next){
  var responseJS = {"result":"create"};
  res.send(responseJS);
  // var noteIn = req.body.note;
  // db.notes.create(noteIn, function(err, noteOut){
  //   if (err) return next(err);
  //   res.send(noteOut);
  // });
};

module.exports.update = function(req, res, next){
  var responseJS = {"result":"update"};
  res.send(responseJS);
  // var id = req.params.id;
  // var noteIn = req.body.note;
  // db.notes.update(id, noteIn, function(err, noteOut){
  //   if (err) return next(err);
  //   res.send(noteOut);
  // });
};

module.exports.show = function(req, res, next){
  var responseJS = {"result":"show"};
  res.send(responseJS);
};

module.exports.patch = function(req, res, next){
  var responseJS = {"result":"patch"};
  res.send(responseJS);
};

module.exports.remove = function(req, res, next){
  var responseJS = {"result":"remove"};
  res.send(responseJS);
};
