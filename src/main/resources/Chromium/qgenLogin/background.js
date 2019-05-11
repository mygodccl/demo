function removeCookies(url) {
    chrome.cookies.getAll({url: url}, function (cookies) {
        if (cookies && cookies.length > 0) {
            for (var i = 0; i < cookies.length; i++) {
                chrome.cookies.remove({url: url, name: cookies[i].name}, function (details) {
                    // console.log(details.name);
                })
            }
        }
    })
}

chrome.webRequest.onBeforeSendHeaders.addListener(
    function (details) {
        window.localStorage.clear();
        removeCookies('https://us-dev-mycis.synnex.org');
        return {requestHeaders: details.requestHeaders};
    },
    // filters
    {urls: ['https://us-dev-mycis.synnex.org/login-portal/**', 'http://us-dev-mycis.synnex.org/login-portal/**']},
    // extraInfoSpec
    ['requestHeaders']);