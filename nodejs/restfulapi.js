var app;
var express = require('express');
var routes = require('./routes');

module.exports = app = express();

app.get('/pages', routes.index);
app.get('/pages/:id', routes.show);
app.post('/pages', routes.create);
app.patch('/pages/:id', routes.patch);
app.put('/pages/:id', routes.update);
app.delete('/pages/:id', routes.remove);

app.listen(3000);