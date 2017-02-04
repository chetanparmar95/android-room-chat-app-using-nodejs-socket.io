var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var counter=0;

app.get('/', function(req, res){
  res.send('<H2>Server started</H2');
});
http.listen(3000, function(){
  console.log('listening on *:3000');
});
io.on('connection', function(socket){
	socket.join("chat");
  if(counter <= 1){
    var total_participants = counter+' participant online';
  }else{
    var total_participants = counter+' participants online';
  }
  var participantsJson = {
    name : '',
    type : 2,
    message : total_participants
  }
  socket.emit("receive message",JSON.stringify(participantsJson));
  counter++;
	console.log('a user connected total:'+counter);
  	socket.on('chat message', function(msg){
    	console.log(msg);
    	socket.broadcast.to('chat').emit("receive message",msg);
  	});
  	socket.on("disconnect room",function(msg){
  		socket.leave('chat');
      counter--;
  		console.log('disconnected total:'+counter);
  		console.log(msg);
  		socket.broadcast.to('chat').emit("receive message",msg);
  	})
});

