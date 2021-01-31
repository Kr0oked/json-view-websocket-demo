var socket = new SockJS('/sock-js');
var client = new Stomp.over(socket);

var connectCallback = function () {
  client.subscribe('/user/response', responseCallback);
};

var responseCallback = function (message) {
  console.log('Response: ' + JSON.stringify(message));
  alert(message);
};

var errorCallback = function (error) {
  console.log('Error: ' + JSON.stringify(error));
  alert(error);
};

client.connect('login', 'passcode', connectCallback, errorCallback);

function triggerMessageWithMessagingTemplate() {
  client.send('/app/messaging-template');
}

function triggerMessageWithSendToUserAnnotation() {
  client.send('/app/send-to-user-annotation');
}
