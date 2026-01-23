function connectWebSocket(pedidoId) {

    const socket = new SockJS('/order/ws-pedidos');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {

        console.log("Conectado al pedido", pedidoId);

        stompClient.subscribe('/topic/pedidos/' + pedidoId, function (message) {

            const pedido = JSON.parse(message.body);
            //document.getElementById('estado').innerText = pedido.estado;
            $('#estado').append(pedido.state);
        });
    });
}

