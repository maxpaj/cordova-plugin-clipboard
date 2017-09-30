var cordova = require('cordova');

/**
 * Clipboard plugin for Cordova
 *
 * @constructor
 */
function Clipboard() {}

/**
 * Sets the clipboard content
 *
 * @param {String}   text      The content to copy to the clipboard
 * @param {String}   mime      The mimetype of the text. Default is "text/plain"
 * @param {Function} onSuccess The function to call in case of success (takes the copied text as argument)
 * @param {Function} onFail    The function to call in case of error
 */
Clipboard.prototype.copy = function(text, mime, onSuccess, onFail) {
  if (typeof text === "undefined" || text === null) text = "";
  if (typeof mime === "undefined" || mime === null) mime = "";
  cordova.exec(onSuccess, onFail, "Clipboard", "copy", [text, mime]);
};

/**
 * Gets the clipboard content
 *
 * @param {Function} onSuccess The function to call in case of success
 * @param {Function} onFail    The function to call in case of error
 */
Clipboard.prototype.paste = function(onSuccess, onFail) {
  cordova.exec(onSuccess, onFail, "Clipboard", "paste", []);
};

// Register the plugin
var clipboard = new Clipboard();
module.exports = clipboard;
