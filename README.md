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
