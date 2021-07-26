 

function getNewGameData() {
    return JSON.parse(JSON.stringify(juegoDefaultData));;
}

$(document).ready(function () {

    $('#siguientePregunta').click(function () {
        cargarProximaPregunta();
    });

    $('#volverAJugar').click(function () {
        juegoData = getNewGameData();
    });

    $('#comenzar').click(function () {
        document.getElementById("gameOver").style.visibility = "hidden";
        juegoData = getNewGameData();
        cargarProximaPregunta();
        //$('#comenzar').css('visibility', 'hidden');
        actualizarVidas();
        $('#questionamiento').css('visibility', 'visible');
        stopSound('gameOverAudio');
    });

    return;
});


function cargarProximaPregunta() {
    $.get('/questionados/next', function (data, status) {


        let pregunta = data; //getPreguntaRando(data);
        //console.log(pregunta)
        $('#categoria').text(pregunta.categoria.nombre);
        $('#pregunta').text(pregunta.enunciado);

        $('#opciones').empty();
        $.each(pregunta.opciones, function (i, opcion) {
            let inputRadio = $('<input type="radio" class="btn-check" autocomplete="off">');
            let uniqueRtaId = 'preg' + pregunta.id + 'rta' + opcion.respuestaId;

            inputRadio.attr('id', uniqueRtaId);
            inputRadio.attr('name', inputRadio.id);


            //inputRadio.click({param1: "Hello", param2: "World"}, cool_function);

            $('#opciones').append(inputRadio);

            let inputLabel = $('<label class="btn btn-primary m-1"></label>');
            inputLabel.attr('for', inputRadio.name);
            inputLabel.text(opcion.texto);
            //inputLabel.click({param1: "Hello", param2: "World"}, cool_function);

            //Al label, al boton no funca
            inputLabel.click({
                preguntaId: pregunta.preguntaId,
                respuestaId: opcion.respuestaId
            }, checkRespuesta);

            inputRadio.appendTo('#opciones');
            inputLabel.appendTo('#opciones');
        });

    });
}

function checkRespuesta(event) {
   //console.log(event)
    let opcion = {
        "preguntaId": event.data.preguntaId,
        "respuestaId": event.data.respuestaId
    }
    //console.log(opcion);
    let elemento = this;
    $.ajax({
        url: "/questionados/verificaciones",
        type: "POST",
        data: JSON.stringify(opcion),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {

            if (data.esCorrecta) {
                festejo(elemento);
            }
            else {
                respuestaErronea(elemento);

            }
            //proxima pregunta en 1 segundo
            setTimeout(() => {
                cargarProximaPregunta();
            }, 1000);

        }
    })


}

function festejo(element) {
    element.classList.add('btn-success');
    party.confetti(element);

}
function respuestaErronea(element) {
    juegoData.vidas--;
    actualizarVidas()
    element.classList.add('btn-danger');

    element.classList.add('active');
    setTimeout(function () {
        element.classList.remove('active');
    }, 1000);

    if (juegoData.vidas <= 0) {
        gameOver()
    }
}

function gameOver() {
    $('#questionamiento').css('visibility', 'hidden');
    $('#gameOver').css('display', 'block');

    //$('#comenzar').css('visibility', 'visible');
    document.getElementById("gameOver").style.visibility = "visible";
    playSound('gameOverAudio');
}

function actualizarVidas() {
    $('#vidas').text(juegoData.vidas);
}

function playSound(soundId) {
    document.getElementById(soundId).play();
};


function stopSound(soundId) {
    //no stop function available
    //document.getElementById(soundId).stop();
};

let juegoDefaultData = {
    nombre: 'Questionados',
    vidas: 3
}
let juegoData = getNewGameData();
