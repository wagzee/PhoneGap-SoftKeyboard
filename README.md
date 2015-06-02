# PhoneGap-SoftKeyboard

Android SoftKeyboard Plugin for PhoneGap.

**NOTE:** The development of this plugin is discontinued. 

## Usage

```javascript

// Init callback for toggle event (every time when softkeyboard is opened or closed the function will be called)
SoftKeyboard.onToggle(function(softKeyBoardHeight) {
  if (softKeyBoardHeight === 0) {
    console.log('Keyboard is closed');
  } else {
    console.log('Keyboard is opened and its height is: ' + softKeyBoardHeight);
  }
});

// Open the soft keyboard.
SoftKeyboard.show();

// Close the soft keyboard.
SoftKeyboard.hide();

// Check whether the keyboard is open or closed.
SoftKeyboard.isShowing(function(isShowing){
  if (isShowing) {  
    console.log('soft keyboard open');
  } else {
    console.log('soft keyboard closed');
  }
}, function(){ 
  console.log('error');
})
```

**HINT:** (undocumented cordova callbacks)
To have callback for hiding and showing the keyboard you can use:
"showkeyboard" / "hidekeyboard" events wich are not mentioned in the docs.

## Example for ExtJS/Sencha touch

```javascript
if (Ext.os.is.Android) {

  SoftKeyboard.onToggle(function(keyboardHeight) {

      // get focused element and body
      var focusedElement = Ext.ComponentQuery.query('input{_isFocused}'),
          body = Ext.DomQuery.select('body');

      // there is nothing to do if there is no body element
      if (!body[0]) {
          return;
      }


      // if there is focused element
      if (focusedElement[0]) {

          // get:
          //  - focused element Y position
          //  - status bar height
          //  - keyboard height
          //  - available area (what is available after keyboard appeared)
          //  - slide height (the amount of slideing up in pixels)
          var fePosition = focusedElement[0].element.getY(),
              statusBarHeight = 50/window.devicePixelRatio,
              softKeyHeight = (keyboardHeight/window.devicePixelRatio) - statusBarHeight,
              availableArea = (Ext.getBody().dom.clientHeight - softKeyHeight)/2,
              slideHeight = fePosition - availableArea;

          // if view would be over slided we should set limit it to keyboard height
          if (slideHeight >= softKeyHeight) {
              slideHeight = softKeyHeight;
          }

          // check if sliding is needed (keyboard would hide the focused input element)
          if (fePosition >= softKeyHeight) {

              if (slideHeight <= 0) {

                  slideHeight = 0;

              }  else  {

                  slideHeight = slideHeight * -1;

              }

              // do the sliding
              body[0].style.webkitTransform = "translateY(" + slideHeight + "px)";
              body[0].style.webkitTransitionDuration = "150ms";

          }


      } else {

          //slide back if there is no focused element
          body[0].style.webkitTransform = "translateY(0px)";

      }

  });
}
```
