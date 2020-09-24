var contextPath = 'http://localhost:8189/tm'

function onLoginClicked() {
    let xhr = new XMLHttpRequest();

    let url = contextPath + '/'

    xhr.open('GET', url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(data));

    if (xhr.status !== 200) {
        // обработать ошибку
        alert(JSON.parse(xhr.response).errorText);
        return;
    }

}