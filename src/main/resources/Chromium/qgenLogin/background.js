function removeHeader(headers, name) {
    for (var i = 0; i < headers.length; i++) {
        if (headers[i].name.toLowerCase() === name) {
            console.log('Removing "' + name + '" header.');
            headers.splice(i, 1);
            break;
        }
    }
}

function removeOneCookie(url, name) {
    chrome.cookies.remove({url: url, name: name}, function (details) {
        console.log(details.name);
    });
}

function removeAllCookies(url) {
    chrome.cookies.getAll({url: url}, function (cookies) {
        for (var i = 0; i < cookies.length; i++) {
            removeOneCookie(url, cookies[i].name);
        }
    });
}

chrome.webRequest.onBeforeSendHeaders.addListener(
    function (details) {
        removeAllCookies("https://www.baidu.com/");
        // removeHeader(details.requestHeaders, 'cookie');
        return {requestHeaders: details.requestHeaders};
    },
    // filters
    {urls: ['https://*/*', 'http://*/*']},
    // extraInfoSpec
    ['requestHeaders']);