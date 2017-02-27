var socket = new SockJS('/sock-js');
var client = new Stomp.over(socket);

var connectCallback = function () {
    client.subscribe('/user/messaging-template-reply', messagingTemplateReplyCallback);
    client.subscribe('/user/send-to-user-reply', sendToUserReplyCallback);

    client.send('/app/messaging-template');
    client.send('/app/send-to-user');
};

var errorCallback = function (error) {
    alert(error);
};

var messagingTemplateReplyCallback = function (message) {
    alert(message);
};

var sendToUserReplyCallback = function (message) {
    alert(message);
};

client.connect('login', 'passcode', connectCallback, errorCallback);
