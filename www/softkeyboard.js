function SoftKeyboard() {}

SoftKeyboard.prototype.show = function(win, fail) {
  return cordova.exec(
      function (args) { if(win) { win(args); } },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "show", []);
};

SoftKeyboard.prototype.hide = function(win, fail) {
  return cordova.exec(
      function (args) { if(win) { win(args); } },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "hide", []);
};

SoftKeyboard.prototype.isShowing = function(win, fail) {
  return cordova.exec(
      function (isShowing) { 
        if(win) { 
          isShowing = isShowing === 'true' ? true : false
          win(isShowing); 
        } 
      },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "isShowing", []);
};

SoftKeyboard.prototype.getWebViewHeight = function(win, fail) {
  return cordova.exec(
      function (height) { 
        if(win) { 
          win(height); 
        } 
      },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "getWebViewHeight", []);
};

SoftKeyboard.prototype.getWebViewWidth = function(win, fail) {
  return cordova.exec(
      function (width) { 
        if(win) { 
          win(width); 
        } 
      },
      function (args) { if(fail) { fail(args); } },
      "SoftKeyboard", "getWebViewWidth", []);
};

SoftKeyboard.prototype.sendKey = function (keyCode, win, fail) {
  return cordova.exec(
      function (args) { if (win) { win(args); } },
      function (args) { if (fail) { fail(args); } },
      "SoftKeyboard", "sendKey", [ keyCode ]);
};

SoftKeyboard.prototype.sendTap = function (posx, posy, win, fail) {
  return cordova.exec(
      function (args) { if (win) { win(args); } },
      function (args) { if (fail) { fail(args); } },
      "SoftKeyboard", "sendTap", [ posx, posy ]);
};

SoftKeyboard.prototype.startTouchLogger = function (win, fail) {
  return cordova.exec(
      function (args) { if (win) { win(args); } },
      function (args) { if (fail) { fail(args); } },
      "SoftKeyboard", "startTouchLogger", [ ]);
};

module.exports = new SoftKeyboard();
