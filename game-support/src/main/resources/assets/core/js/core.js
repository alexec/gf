core = {
    coin: 0.01,
    get: function (path, done, fail) {
        $.getJSON({
            url: path
        })
            .done(done)
            .fail(function (x, t, r) {
                (fail || function () {
                })();
                alert(r);
            });
    },
    post: function (path, data, done, fail) {
        $.post({
            url: path,
            data: JSON.stringify(data),
            contentType: 'application/json'
        })
            .done(done)
            .fail(function (x, t, r) {
                (fail || function () {
                })();
                alert(r);
            });
    },
    getBalance: function () {
        return parseFloat(document.getElementById("balance").innerHTML);
    },
    setBalance: function (balance) {
        document.getElementById("balance").innerHTML = balance;
    },
    addButton: function (text, fn) {
        var b = document.createElement("button");
        b.innerHTML = text;
        b.onclick = fn;
        document.getElementById("buttons").appendChild(b);
    },
    enableButton: function () {
    },
    disableButton: function () {
    }
};