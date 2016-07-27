/**
 * Created by ZJDX on 2016/7/7.
 */
//创建sockJS协议
var stompClient = null;

function connect() {
    // 开启socket连接
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (fra) {
        console.log("connect!"+fra);
        //成功连接后，设定接受服务器的地址和处理方法
        stompClient.subscribe('/topic/getLocation', function (frame) {
            //服务器返回请求处理
            console.log("getLocation!");
            var coordinate=eval(frame);
            var coordinateJson = JSON.parse(frame.body);
            console.log(coordinateJson);
        }, function (error) {
            //连接出现错误回调函数
            alert(error);
        });
    }, function (error) {
        //连接出现错误回调函数
        alert(error);
    });
}
// 断开socket连接
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
// 向‘/app/change-notice’服务端发送消息
function sendName() {
    var value = 123;
    stompClient.send("/app/location", {}, value);
}

