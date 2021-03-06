define("js/plupload-debug", [], function(require, exports, module) {
    return function(jQuery) {
        /**
 * plupload.js
 * 
 * Copyright 2009, Moxiecode Systems AB Released under GPL License.
 * 
 * License: http://www.plupload.com/license Contributing:
 * http://www.plupload.com/contributing
 */
        // JSLint defined globals
        /* global window:false, escape:false */
        /* !@@version@@ */
        (function() {
            var count = 0, runtimes = [], i18n = {}, mimes = {}, xmlEncodeChars = {
                "<": "lt",
                ">": "gt",
                "&": "amp",
                '"': "quot",
                "'": "#39"
            }, xmlEncodeRegExp = /[<>&\"\']/g, undef, delay = window.setTimeout, // A place to store references to event handlers
            eventhash = {}, uid;
            // IE W3C like event funcs
            function preventDefault() {
                this.returnValue = false;
            }
            function stopPropagation() {
                this.cancelBubble = true;
            }
            // Parses the default mime types string into a mimes lookup map
            (function(mime_data) {
                var items = mime_data.split(/,/), i, y, ext;
                for (i = 0; i < items.length; i += 2) {
                    ext = items[i + 1].split(/ /);
                    for (y = 0; y < ext.length; y++) {
                        mimes[ext[y]] = items[i];
                    }
                }
            })("application/msword,doc dot," + "application/pdf,pdf," + "application/pgp-signature,pgp," + "application/postscript,ps ai eps," + "application/rtf,rtf," + "application/vnd.ms-excel,xls xlb," + "application/vnd.ms-powerpoint,ppt pps pot," + "application/zip,zip," + "application/x-shockwave-flash,swf swfl," + "application/vnd.openxmlformats-officedocument.wordprocessingml.document,docx," + "application/vnd.openxmlformats-officedocument.wordprocessingml.template,dotx," + "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,xlsx," + "application/vnd.openxmlformats-officedocument.presentationml.presentation,pptx," + "application/vnd.openxmlformats-officedocument.presentationml.template,potx," + "application/vnd.openxmlformats-officedocument.presentationml.slideshow,ppsx," + "application/x-javascript,js," + "application/json,json," + "audio/mpeg,mpga mpega mp2 mp3," + "audio/x-wav,wav," + "audio/mp4,m4a," + "image/bmp,bmp," + "image/gif,gif," + "image/jpeg,jpeg jpg jpe," + "image/photoshop,psd," + "image/png,png," + "image/svg+xml,svg svgz," + "image/tiff,tiff tif," + "text/plain,asc txt text diff log," + "text/html,htm html xhtml," + "text/css,css," + "text/csv,csv," + "text/rtf,rtf," + "video/mpeg,mpeg mpg mpe," + "video/quicktime,qt mov," + "video/mp4,mp4," + "video/x-m4v,m4v," + "video/x-flv,flv," + "video/x-ms-wmv,wmv," + "video/avi,avi," + "video/webm,webm," + "video/vnd.rn-realvideo,rv," + "application/vnd.oasis.opendocument.formula-template,otf," + "application/octet-stream,exe");
            /**
			 * Plupload class with some global constants and functions.
			 * 
			 * @example // Encode entities
			 * console.log(plupload.xmlEncode("My string &lt;&gt;"));
			 *  // Generate unique id
			 * console.log(plupload.guid());
			 * 
			 * @static
			 * @class plupload
			 */
            var plupload = {
                /**
				 * Plupload version will be replaced on build.
				 */
                VERSION: "@@version@@",
                /**
				 * Inital state of the queue and also the state ones it's
				 * finished all it's uploads.
				 * 
				 * @property STOPPED
				 * @final
				 */
                STOPPED: 1,
                /**
				 * Upload process is running
				 * 
				 * @property STARTED
				 * @final
				 */
                STARTED: 2,
                /**
				 * File is queued for upload
				 * 
				 * @property QUEUED
				 * @final
				 */
                QUEUED: 1,
                /**
				 * File is being uploaded
				 * 
				 * @property UPLOADING
				 * @final
				 */
                UPLOADING: 2,
                /**
				 * File has failed to be uploaded
				 * 
				 * @property FAILED
				 * @final
				 */
                FAILED: 4,
                /**
				 * File has been uploaded successfully
				 * 
				 * @property DONE
				 * @final
				 */
                DONE: 5,
                // Error constants used by the Error event
                /**
				 * Generic error for example if an exception is thrown inside
				 * Silverlight.
				 * 
				 * @property GENERIC_ERROR
				 * @final
				 */
                GENERIC_ERROR: -100,
                /**
				 * HTTP transport error. For example if the server produces a
				 * HTTP status other than 200.
				 * 
				 * @property HTTP_ERROR
				 * @final
				 */
                HTTP_ERROR: -200,
                /**
				 * Generic I/O error. For exampe if it wasn't possible to open
				 * the file stream on local machine.
				 * 
				 * @property IO_ERROR
				 * @final
				 */
                IO_ERROR: -300,
                /**
				 * Generic I/O error. For exampe if it wasn't possible to open
				 * the file stream on local machine.
				 * 
				 * @property SECURITY_ERROR
				 * @final
				 */
                SECURITY_ERROR: -400,
                /**
				 * Initialization error. Will be triggered if no runtime was
				 * initialized.
				 * 
				 * @property INIT_ERROR
				 * @final
				 */
                INIT_ERROR: -500,
                /**
				 * File size error. If the user selects a file that is too large
				 * it will be blocked and an error of this type will be
				 * triggered.
				 * 
				 * @property FILE_SIZE_ERROR
				 * @final
				 */
                FILE_SIZE_ERROR: -600,
                /**
				 * File extension error. If the user selects a file that isn't
				 * valid according to the filters setting.
				 * 
				 * @property FILE_EXTENSION_ERROR
				 * @final
				 */
                FILE_EXTENSION_ERROR: -601,
                /**
				 * Runtime will try to detect if image is proper one. Otherwise
				 * will throw this error.
				 * 
				 * @property IMAGE_FORMAT_ERROR
				 * @final
				 */
                IMAGE_FORMAT_ERROR: -700,
                /**
				 * While working on the image runtime will try to detect if the
				 * operation may potentially run out of memeory and will throw
				 * this error.
				 * 
				 * @property IMAGE_MEMORY_ERROR
				 * @final
				 */
                IMAGE_MEMORY_ERROR: -701,
                /**
				 * Each runtime has an upper limit on a dimension of the image
				 * it can handle. If bigger, will throw this error.
				 * 
				 * @property IMAGE_DIMENSIONS_ERROR
				 * @final
				 */
                IMAGE_DIMENSIONS_ERROR: -702,
                /**
				 * Mime type lookup table.
				 * 
				 * @property mimeTypes
				 * @type Object
				 * @final
				 */
                mimeTypes: mimes,
                /**
				 * In some cases sniffing is the only way around :(
				 */
                ua: function() {
                    var nav = navigator, userAgent = nav.userAgent, vendor = nav.vendor, webkit, opera, safari;
                    webkit = /WebKit/.test(userAgent);
                    safari = webkit && vendor.indexOf("Apple") !== -1;
                    opera = window.opera && window.opera.buildNumber;
                    return {
                        windows: navigator.platform.indexOf("Win") !== -1,
                        ie: !webkit && !opera && /MSIE/gi.test(userAgent) && /Explorer/gi.test(nav.appName),
                        webkit: webkit,
                        gecko: !webkit && /Gecko/.test(userAgent),
                        safari: safari,
                        opera: !!opera
                    };
                }(),
                /**
				 * Gets the true type of the built-in object (better version of
				 * typeof).
				 * 
				 * @credits Angus Croll (http://javascriptweblog.wordpress.com/)
				 * 
				 * @param {Object}
				 *            o Object to check.
				 * @return {String} Object [[Class]]
				 */
                typeOf: function(o) {
                    return {}.toString.call(o).match(/\s([a-z|A-Z]+)/)[1].toLowerCase();
                },
                /**
				 * Extends the specified object with another object.
				 * 
				 * @method extend
				 * @param {Object}
				 *            target Object to extend.
				 * @param {Object..}
				 *            obj Multiple objects to extend with.
				 * @return {Object} Same as target, the extended object.
				 */
                extend: function(target) {
                    plupload.each(arguments, function(arg, i) {
                        if (i > 0) {
                            plupload.each(arg, function(value, key) {
                                target[key] = value;
                            });
                        }
                    });
                    return target;
                },
                /**
				 * Cleans the specified name from national characters
				 * (diacritics). The result will be a name with only a-z, 0-9
				 * and _.
				 * 
				 * @method cleanName
				 * @param {String}
				 *            s String to clean up.
				 * @return {String} Cleaned string.
				 */
                cleanName: function(name) {
                    var i, lookup;
                    // Replace diacritics
                    lookup = [ /[\300-\306]/g, "A", /[\340-\346]/g, "a", /\307/g, "C", /\347/g, "c", /[\310-\313]/g, "E", /[\350-\353]/g, "e", /[\314-\317]/g, "I", /[\354-\357]/g, "i", /\321/g, "N", /\361/g, "n", /[\322-\330]/g, "O", /[\362-\370]/g, "o", /[\331-\334]/g, "U", /[\371-\374]/g, "u" ];
                    for (i = 0; i < lookup.length; i += 2) {
                        name = name.replace(lookup[i], lookup[i + 1]);
                    }
                    // Replace whitespace
                    name = name.replace(/\s+/g, "_");
                    // Remove anything else
                    name = name.replace(/[^a-z0-9_\-\.]+/gi, "");
                    return name;
                },
                /**
				 * Adds a specific upload runtime like for example flash or
				 * gears.
				 * 
				 * @method addRuntime
				 * @param {String}
				 *            name Runtime name for example flash.
				 * @param {Object}
				 *            obj Object containing init/destroy method.
				 */
                addRuntime: function(name, runtime) {
                    runtime.name = name;
                    runtimes[name] = runtime;
                    runtimes.push(runtime);
                    return runtime;
                },
                /**
				 * Generates an unique ID. This is 99.99% unique since it takes
				 * the current time and 5 random numbers. The only way a user
				 * would be able to get the same ID is if the two persons at the
				 * same exact milisecond manages to get 5 the same random
				 * numbers between 0-65535 it also uses a counter so each call
				 * will be guaranteed to be page unique. It's more probable for
				 * the earth to be hit with an ansteriod. You can also if you
				 * want to be 100% sure set the plupload.guidPrefix property to
				 * an user unique key.
				 * 
				 * @method guid
				 * @return {String} Virtually unique id.
				 */
                guid: function() {
                    var guid = new Date().getTime().toString(32), i;
                    for (i = 0; i < 5; i++) {
                        guid += Math.floor(Math.random() * 65535).toString(32);
                    }
                    return (plupload.guidPrefix || "p") + guid + (count++).toString(32);
                },
                /**
				 * Builds a full url out of a base URL and an object with items
				 * to append as query string items.
				 * 
				 * @param {String}
				 *            url Base URL to append query string items to.
				 * @param {Object}
				 *            items Name/value object to serialize as a
				 *            querystring.
				 * @return {String} String with url + serialized query string
				 *         items.
				 */
                buildUrl: function(url, items) {
                    var query = "";
                    plupload.each(items, function(value, name) {
                        query += (query ? "&" : "") + encodeURIComponent(name) + "=" + encodeURIComponent(value);
                    });
                    if (query) {
                        url += (url.indexOf("?") > 0 ? "&" : "?") + query;
                    }
                    return url;
                },
                /**
				 * Executes the callback function for each item in array/object.
				 * If you return false in the callback it will break the loop.
				 * 
				 * @param {Object}
				 *            obj Object to iterate.
				 * @param {function}
				 *            callback Callback function to execute for each
				 *            item.
				 */
                each: function(obj, callback) {
                    var length, key, i;
                    if (obj) {
                        length = obj.length;
                        if (length === undef) {
                            // Loop object items
                            for (key in obj) {
                                if (obj.hasOwnProperty(key)) {
                                    if (callback(obj[key], key) === false) {
                                        return;
                                    }
                                }
                            }
                        } else {
                            // Loop array items
                            for (i = 0; i < length; i++) {
                                if (callback(obj[i], i) === false) {
                                    return;
                                }
                            }
                        }
                    }
                },
                /**
				 * Formats the specified number as a size string for example
				 * 1024 becomes 1 KB.
				 * 
				 * @method formatSize
				 * @param {Number}
				 *            size Size to format as string.
				 * @return {String} Formatted size string.
				 */
                formatSize: function(size) {
                    if (size === undef || /\D/.test(size)) {
                        return plupload.translate("N/A");
                    }
                    // GB
                    if (size > 1073741824) {
                        return Math.round(size / 1073741824, 1) + " GB";
                    }
                    // MB
                    if (size > 1048576) {
                        return Math.round(size / 1048576, 1) + " MB";
                    }
                    // KB
                    if (size > 1024) {
                        return Math.round(size / 1024, 1) + " KB";
                    }
                    return size + " b";
                },
                /**
				 * Returns the absolute x, y position of an Element. The
				 * position will be returned in a object with x, y fields.
				 * 
				 * @method getPos
				 * @param {Element}
				 *            node HTML element or element id to get x, y
				 *            position from.
				 * @param {Element}
				 *            root Optional root element to stop calculations
				 *            at.
				 * @return {object} Absolute position of the specified element
				 *         object with x, y fields.
				 */
                getPos: function(node, root) {
                    var x = 0, y = 0, parent, doc = document, nodeRect, rootRect;
                    node = node;
                    root = root || doc.body;
                    // Returns the x, y cordinate for an element on IE 6 and IE
                    // 7
                    function getIEPos(node) {
                        var bodyElm, rect, x = 0, y = 0;
                        if (node) {
                            rect = node.getBoundingClientRect();
                            bodyElm = doc.compatMode === "CSS1Compat" ? doc.documentElement : doc.body;
                            x = rect.left + bodyElm.scrollLeft;
                            y = rect.top + bodyElm.scrollTop;
                        }
                        return {
                            x: x,
                            y: y
                        };
                    }
                    // Use getBoundingClientRect on IE 6 and IE 7 but not on IE
                    // 8 in standards mode
                    if (node && node.getBoundingClientRect && navigator.userAgent.indexOf("MSIE") > 0 && doc.documentMode < 8) {
                        nodeRect = getIEPos(node);
                        rootRect = getIEPos(root);
                        return {
                            x: nodeRect.x - rootRect.x,
                            y: nodeRect.y - rootRect.y
                        };
                    }
                    parent = node;
                    while (parent && parent != root && parent.nodeType) {
                        x += parent.offsetLeft || 0;
                        y += parent.offsetTop || 0;
                        parent = parent.offsetParent;
                    }
                    parent = node.parentNode;
                    while (parent && parent != root && parent.nodeType) {
                        x -= parent.scrollLeft || 0;
                        y -= parent.scrollTop || 0;
                        parent = parent.parentNode;
                    }
                    return {
                        x: x,
                        y: y
                    };
                },
                /**
				 * Returns the size of the specified node in pixels.
				 * 
				 * @param {Node}
				 *            node Node to get the size of.
				 * @return {Object} Object with a w and h property.
				 */
                getSize: function(node) {
                    return {
                        w: node.offsetWidth || node.clientWidth,
                        h: node.offsetHeight || node.clientHeight
                    };
                },
                /**
				 * Parses the specified size string into a byte value. For
				 * example 10kb becomes 10240.
				 * 
				 * @method parseSize
				 * @param {String/Number}
				 *            size String to parse or number to just pass
				 *            through.
				 * @return {Number} Size in bytes.
				 */
                parseSize: function(size) {
                    var mul;
                    if (typeof size == "string") {
                        size = /^([0-9]+)([mgk]?)$/.exec(size.toLowerCase().replace(/[^0-9mkg]/g, ""));
                        mul = size[2];
                        size = +size[1];
                        if (mul == "g") {
                            size *= 1073741824;
                        }
                        if (mul == "m") {
                            size *= 1048576;
                        }
                        if (mul == "k") {
                            size *= 1024;
                        }
                    }
                    return size;
                },
                /**
				 * Encodes the specified string.
				 * 
				 * @method xmlEncode
				 * @param {String}
				 *            s String to encode.
				 * @return {String} Encoded string.
				 */
                xmlEncode: function(str) {
                    return str ? ("" + str).replace(xmlEncodeRegExp, function(chr) {
                        return xmlEncodeChars[chr] ? "&" + xmlEncodeChars[chr] + ";" : chr;
                    }) : str;
                },
                /**
				 * Forces anything into an array.
				 * 
				 * @method toArray
				 * @param {Object}
				 *            obj Object with length field.
				 * @return {Array} Array object containing all items.
				 */
                toArray: function(obj) {
                    var i, arr = [];
                    for (i = 0; i < obj.length; i++) {
                        arr[i] = obj[i];
                    }
                    return arr;
                },
                /**
				 * Find an element in array and return it's index if present,
				 * otherwise return -1.
				 * 
				 * @method inArray
				 * @param {mixed}
				 *            needle Element to find
				 * @param {Array}
				 *            array
				 * @return {Int} Index of the element, or -1 if not found
				 */
                inArray: function(needle, array) {
                    if (array) {
                        if (Array.prototype.indexOf) {
                            return Array.prototype.indexOf.call(array, needle);
                        }
                        for (var i = 0, length = array.length; i < length; i++) {
                            if (array[i] === needle) {
                                return i;
                            }
                        }
                    }
                    return -1;
                },
                /**
				 * Extends the language pack object with new items.
				 * 
				 * @param {Object}
				 *            pack Language pack items to add.
				 * @return {Object} Extended language pack object.
				 */
                addI18n: function(pack) {
                    return plupload.extend(i18n, pack);
                },
                /**
				 * Translates the specified string by checking for the english
				 * string in the language pack lookup.
				 * 
				 * @param {String}
				 *            str String to look for.
				 * @return {String} Translated string or the input string if it
				 *         wasn't found.
				 */
                translate: function(str) {
                    return i18n[str] || str;
                },
                /**
				 * Checks if object is empty.
				 * 
				 * @param {Object}
				 *            obj Object to check.
				 * @return {Boolean}
				 */
                isEmptyObj: function(obj) {
                    if (obj === undef) return true;
                    for (var prop in obj) {
                        return false;
                    }
                    return true;
                },
                /**
				 * Checks if specified DOM element has specified class.
				 * 
				 * @param {Object}
				 *            obj DOM element like object to add handler to.
				 * @param {String}
				 *            name Class name
				 */
                hasClass: function(obj, name) {
                    var regExp;
                    if (obj.className == "") {
                        return false;
                    }
                    regExp = new RegExp("(^|\\s+)" + name + "(\\s+|$)");
                    return regExp.test(obj.className);
                },
                /**
				 * Adds specified className to specified DOM element.
				 * 
				 * @param {Object}
				 *            obj DOM element like object to add handler to.
				 * @param {String}
				 *            name Class name
				 */
                addClass: function(obj, name) {
                    if (!plupload.hasClass(obj, name)) {
                        obj.className = obj.className == "" ? name : obj.className.replace(/\s+$/, "") + " " + name;
                    }
                },
                /**
				 * Removes specified className from specified DOM element.
				 * 
				 * @param {Object}
				 *            obj DOM element like object to add handler to.
				 * @param {String}
				 *            name Class name
				 */
                removeClass: function(obj, name) {
                    var regExp = new RegExp("(^|\\s+)" + name + "(\\s+|$)");
                    obj.className = obj.className.replace(regExp, function($0, $1, $2) {
                        return $1 === " " && $2 === " " ? " " : "";
                    });
                },
                /**
				 * Returns a given computed style of a DOM element.
				 * 
				 * @param {Object}
				 *            obj DOM element like object.
				 * @param {String}
				 *            name Style you want to get from the DOM element
				 */
                getStyle: function(obj, name) {
                    if (obj.currentStyle) {
                        return obj.currentStyle[name];
                    } else if (window.getComputedStyle) {
                        return window.getComputedStyle(obj, null)[name];
                    }
                },
                /**
				 * Adds an event handler to the specified object and store
				 * reference to the handler in objects internal Plupload
				 * registry (@see removeEvent).
				 * 
				 * @param {Object}
				 *            obj DOM element like object to add handler to.
				 * @param {String}
				 *            name Name to add event listener to.
				 * @param {Function}
				 *            callback Function to call when event occurs.
				 * @param {String}
				 *            (optional) key that might be used to add specifity
				 *            to the event record.
				 */
                addEvent: function(obj, name, callback) {
                    var func, events, types, key;
                    // if passed in, event will be locked with this key - one
                    // would need to provide it to removeEvent
                    key = arguments[3];
                    name = name.toLowerCase();
                    // Initialize unique identifier if needed
                    if (uid === undef) {
                        uid = "Plupload_" + plupload.guid();
                    }
                    // Add event listener
                    if (obj.addEventListener) {
                        func = callback;
                        obj.addEventListener(name, func, false);
                    } else if (obj.attachEvent) {
                        func = function() {
                            var evt = window.event;
                            if (!evt.target) {
                                evt.target = evt.srcElement;
                            }
                            evt.preventDefault = preventDefault;
                            evt.stopPropagation = stopPropagation;
                            callback(evt);
                        };
                        obj.attachEvent("on" + name, func);
                    }
                    // Log event handler to objects internal Plupload registry
                    if (obj[uid] === undef) {
                        obj[uid] = plupload.guid();
                    }
                    if (!eventhash.hasOwnProperty(obj[uid])) {
                        eventhash[obj[uid]] = {};
                    }
                    events = eventhash[obj[uid]];
                    if (!events.hasOwnProperty(name)) {
                        events[name] = [];
                    }
                    events[name].push({
                        func: func,
                        orig: callback,
                        // store original callback
                        // for IE
                        key: key
                    });
                },
                /**
				 * Remove event handler from the specified object. If third
				 * argument (callback) is not specified remove all events with
				 * the specified name.
				 * 
				 * @param {Object}
				 *            obj DOM element to remove event listener(s) from.
				 * @param {String}
				 *            name Name of event listener to remove.
				 * @param {Function|String}
				 *            (optional) might be a callback or unique key to
				 *            match.
				 */
                removeEvent: function(obj, name) {
                    var type, callback, key;
                    // match the handler either by callback or by key
                    if (typeof arguments[2] == "function") {
                        callback = arguments[2];
                    } else {
                        key = arguments[2];
                    }
                    name = name.toLowerCase();
                    if (obj[uid] && eventhash[obj[uid]] && eventhash[obj[uid]][name]) {
                        type = eventhash[obj[uid]][name];
                    } else {
                        return;
                    }
                    for (var i = type.length - 1; i >= 0; i--) {
                        // undefined or not, key should match
                        if (type[i].key === key || type[i].orig === callback) {
                            if (obj.removeEventListener) {
                                obj.removeEventListener(name, type[i].func, false);
                            } else if (obj.detachEvent) {
                                obj.detachEvent("on" + name, type[i].func);
                            }
                            type[i].orig = null;
                            type[i].func = null;
                            type.splice(i, 1);
                            // If callback was passed we are done here,
                            // otherwise proceed
                            if (callback !== undef) {
                                break;
                            }
                        }
                    }
                    // If event array got empty, remove it
                    if (!type.length) {
                        delete eventhash[obj[uid]][name];
                    }
                    // If Plupload registry has become empty, remove it
                    if (plupload.isEmptyObj(eventhash[obj[uid]])) {
                        delete eventhash[obj[uid]];
                        // IE doesn't let you remove DOM object property with -
                        // delete
                        try {
                            delete obj[uid];
                        } catch (e) {
                            obj[uid] = undef;
                        }
                    }
                },
                /**
				 * Remove all kind of events from the specified object
				 * 
				 * @param {Object}
				 *            obj DOM element to remove event listeners from.
				 * @param {String}
				 *            (optional) unique key to match, when removing
				 *            events.
				 */
                removeAllEvents: function(obj) {
                    var key = arguments[1];
                    if (obj[uid] === undef || !obj[uid]) {
                        return;
                    }
                    plupload.each(eventhash[obj[uid]], function(events, name) {
                        plupload.removeEvent(obj, name, key);
                    });
                }
            };
            /**
			 * Uploader class, an instance of this class will be created for
			 * each upload field.
			 * 
			 * @example
			 * var uploader = new plupload.Uploader({
			 *     runtimes : 'gears,html5,flash',
			 *     browse_button : 'button_id'
			 * });
			 * 
			 * uploader.bind('Init', function(up) {
			 *     alert('Supports drag/drop: ' + (!!up.features.dragdrop));
			 * });
			 * 
			 * uploader.bind('FilesAdded', function(up, files) {
			 *     alert('Selected files: ' + files.length);
			 * });
			 * 
			 * uploader.bind('QueueChanged', function(up) {
			 *     alert('Queued files: ' + uploader.files.length);
			 * });
			 * 
			 * uploader.init();
			 * 
			 * @class plupload.Uploader
			 */
            /**
			 * Constructs a new uploader instance.
			 * 
			 * @constructor
			 * @method Uploader
			 * @param {Object}
			 *            settings Initialization settings, to be used by the
			 *            uploader instance and runtimes.
			 */
            plupload.Uploader = function(settings) {
                var events = {}, total, files = [], startTime, disabled = false;
                // Inital total state
                total = new plupload.QueueProgress();
                // Default settings
                settings = plupload.extend({
                    chunk_size: 0,
                    multipart: true,
                    multi_selection: true,
                    file_data_name: "file",
                    filters: []
                }, settings);
                // Private methods
                function uploadNext() {
                    var file, count = 0, i;
                    if (this.state == plupload.STARTED) {
                        // Find first QUEUED file
                        for (i = 0; i < files.length; i++) {
                            if (!file && files[i].status == plupload.QUEUED) {
                                file = files[i];
                                file.status = plupload.UPLOADING;
                                if (this.trigger("BeforeUpload", file)) {
                                    this.trigger("UploadFile", file);
                                }
                            } else {
                                count++;
                            }
                        }
                        // All files are DONE or FAILED
                        if (count == files.length) {
                            this.stop();
                            this.trigger("UploadComplete", files);
                        }
                    }
                }
                function calc() {
                    var i, file;
                    // Reset stats
                    total.reset();
                    // Check status, size, loaded etc on all files
                    for (i = 0; i < files.length; i++) {
                        file = files[i];
                        if (file.size !== undef) {
                            total.size += file.size;
                            total.loaded += file.loaded;
                        } else {
                            total.size = undef;
                        }
                        if (file.status == plupload.DONE) {
                            total.uploaded++;
                        } else if (file.status == plupload.FAILED) {
                            total.failed++;
                        } else {
                            total.queued++;
                        }
                    }
                    // If we couldn't calculate a total file size then use the
                    // number of files to calc percent
                    if (total.size === undef) {
                        total.percent = files.length > 0 ? Math.ceil(total.uploaded / files.length * 100) : 0;
                    } else {
                        total.bytesPerSec = Math.ceil(total.loaded / ((+new Date() - startTime || 1) / 1e3));
                        total.percent = total.size > 0 ? Math.ceil(total.loaded / total.size * 100) : 0;
                    }
                }
                // Add public methods
                plupload.extend(this, {
                    /**
					 * Current state of the total uploading progress. This one
					 * can either be plupload.STARTED or plupload.STOPPED. These
					 * states are controlled by the stop/start methods. The
					 * default value is STOPPED.
					 * 
					 * @property state
					 * @type Number
					 */
                    state: plupload.STOPPED,
                    /**
					 * Current runtime name.
					 * 
					 * @property runtime
					 * @type String
					 */
                    runtime: "",
                    /**
					 * Map of features that are available for the uploader
					 * runtime. Features will be filled before the init event is
					 * called, these features can then be used to alter the UI
					 * for the end user. Some of the current features that might
					 * be in this map is: dragdrop, chunks, jpgresize,
					 * pngresize.
					 * 
					 * @property features
					 * @type Object
					 */
                    features: {},
                    /**
					 * Current upload queue, an array of File instances.
					 * 
					 * @property files
					 * @type Array
					 * @see plupload.File
					 */
                    files: files,
                    /**
					 * Object with name/value settings.
					 * 
					 * @property settings
					 * @type Object
					 */
                    settings: settings,
                    /**
					 * Total progess information. How many files has been
					 * uploaded, total percent etc.
					 * 
					 * @property total
					 * @type plupload.QueueProgress
					 */
                    total: total,
                    /**
					 * Unique id for the Uploader instance.
					 * 
					 * @property id
					 * @type String
					 */
                    id: plupload.guid(),
                    /**
					 * Initializes the Uploader instance and adds internal event
					 * listeners.
					 * 
					 * @method init
					 */
                    init: function() {
                        var self = this, i, runtimeList, a, runTimeIndex = 0, items;
                        if (typeof settings.preinit == "function") {
                            settings.preinit(self);
                        } else {
                            plupload.each(settings.preinit, function(func, name) {
                                self.bind(name, func);
                            });
                        }
                        settings.page_url = settings.page_url || document.location.pathname.replace(/\/[^\/]+$/g, "/");
                        // If url is relative force it absolute to the current
                        // page
                        if (!/^(\w+:\/\/|\/)/.test(settings.url)) {
                            settings.url = settings.page_url + settings.url;
                        }
                        // Convert settings
                        settings.chunk_size = plupload.parseSize(settings.chunk_size);
                        settings.max_file_size = plupload.parseSize(settings.max_file_size);
                        // Add files to queue
                        self.bind("FilesAdded", function(up, selected_files) {
                            var i, file, count = 0, extensionsRegExp, filters = settings.filters;
                            // Convert extensions to regexp
                            if (filters && filters.length) {
                                extensionsRegExp = [];
                                plupload.each(filters, function(filter) {
                                    plupload.each(filter.extensions.split(/,/), function(ext) {
                                        if (/^\s*\*\s*$/.test(ext)) {
                                            extensionsRegExp.push("\\.*");
                                        } else {
                                            extensionsRegExp.push("\\." + ext.replace(new RegExp("[" + "/^$.*+?|()[]{}\\".replace(/./g, "\\$&") + "]", "g"), "\\$&"));
                                        }
                                    });
                                });
                                extensionsRegExp = new RegExp(extensionsRegExp.join("|") + "$", "i");
                            }
                            for (i = 0; i < selected_files.length; i++) {
                                file = selected_files[i];
                                file.loaded = 0;
                                file.percent = 0;
                                file.status = plupload.QUEUED;
                                // Invalid file extension
                                if (extensionsRegExp && !extensionsRegExp.test(file.name)) {
                                    up.trigger("Error", {
                                        code: plupload.FILE_EXTENSION_ERROR,
                                        message: plupload.translate("File extension error."),
                                        file: file
                                    });
                                    continue;
                                }
                                // Invalid file size
                                if (file.size !== undef && file.size > settings.max_file_size) {
                                    up.trigger("Error", {
                                        code: plupload.FILE_SIZE_ERROR,
                                        message: plupload.translate("File size error."),
                                        file: file
                                    });
                                    continue;
                                }
                                // Add valid file to list
                                files.push(file);
                                count++;
                            }
                            // Only trigger QueueChanged event if any files
                            // where added
                            if (count) {
                                delay(function() {
                                    self.trigger("QueueChanged");
                                    self.refresh();
                                }, 1);
                            } else {
                                return false;
                            }
                        });
                        // Generate unique target filenames
                        if (settings.unique_names) {
                            self.bind("UploadFile", function(up, file) {
                                var matches = file.name.match(/\.([^.]+)$/), ext = "tmp";
                                if (matches) {
                                    ext = matches[1];
                                }
                                file.target_name = file.id + "." + ext;
                            });
                        }
                        self.bind("UploadProgress", function(up, file) {
                            file.percent = file.size > 0 ? Math.ceil(file.loaded / file.size * 100) : 100;
                            calc();
                        });
                        self.bind("StateChanged", function(up) {
                            if (up.state == plupload.STARTED) {
                                // Get start time to calculate bps
                                startTime = +new Date();
                            } else if (up.state == plupload.STOPPED) {
                                // Reset currently uploading files
                                for (i = up.files.length - 1; i >= 0; i--) {
                                    if (up.files[i].status == plupload.UPLOADING) {
                                        up.files[i].status = plupload.QUEUED;
                                        calc();
                                    }
                                }
                            }
                        });
                        self.bind("QueueChanged", calc);
                        self.bind("Error", function(up, err) {
                            // Set failed status if an error occured on
                            // a file
                            if (err.file) {
                                err.file.status = plupload.FAILED;
                                calc();
                                // Upload next file but detach it from
                                // the error event
                                // since other custom listeners might
                                // want to stop the queue
                                if (up.state == plupload.STARTED) {
                                    delay(function() {
                                        uploadNext.call(self);
                                    }, 1);
                                }
                            }
                        });
                        self.bind("FileUploaded", function(up, file) {
                            file.status = plupload.DONE;
                            file.loaded = file.size;
                            up.trigger("UploadProgress", file);
                            // Upload next file but detach it from the
                            // error event
                            // since other custom listeners might want
                            // to stop the queue
                            delay(function() {
                                uploadNext.call(self);
                            }, 1);
                        });
                        // Setup runtimeList
                        if (settings.runtimes) {
                            runtimeList = [];
                            items = settings.runtimes.split(/\s?,\s?/);
                            for (i = 0; i < items.length; i++) {
                                if (runtimes[items[i]]) {
                                    runtimeList.push(runtimes[items[i]]);
                                }
                            }
                        } else {
                            runtimeList = runtimes;
                        }
                        // Call init on each runtime in sequence
                        function callNextInit() {
                            var runtime = runtimeList[runTimeIndex++], features, requiredFeatures, i;
                            if (runtime) {
                                features = runtime.getFeatures();
                                // Check if runtime supports required features
                                requiredFeatures = self.settings.required_features;
                                if (requiredFeatures) {
                                    requiredFeatures = requiredFeatures.split(",");
                                    for (i = 0; i < requiredFeatures.length; i++) {
                                        // Specified feature doesn't exist
                                        if (!features[requiredFeatures[i]]) {
                                            callNextInit();
                                            return;
                                        }
                                    }
                                }
                                // Try initializing the runtime
                                runtime.init(self, function(res) {
                                    if (res && res.success) {
                                        // Successful initialization
                                        self.features = features;
                                        self.runtime = runtime.name;
                                        self.trigger("Init", {
                                            runtime: runtime.name
                                        });
                                        self.trigger("PostInit");
                                        self.refresh();
                                    } else {
                                        callNextInit();
                                    }
                                });
                            } else {
                                // Trigger an init error if we run out of
                                // runtimes
                                self.trigger("Error", {
                                    code: plupload.INIT_ERROR,
                                    message: plupload.translate("Init error.")
                                });
                            }
                        }
                        callNextInit();
                        if (typeof settings.init == "function") {
                            settings.init(self);
                        } else {
                            plupload.each(settings.init, function(func, name) {
                                self.bind(name, func);
                            });
                        }
                    },
                    /**
					 * Refreshes the upload instance by dispatching out a
					 * refresh event to all runtimes. This would for example
					 * reposition flash/silverlight shims on the page.
					 * 
					 * @method refresh
					 */
                    refresh: function() {
                        this.trigger("Refresh");
                    },
                    /**
					 * Starts uploading the queued files.
					 * 
					 * @method start
					 */
                    start: function() {
                        if (files.length && this.state != plupload.STARTED) {
                            this.state = plupload.STARTED;
                            this.trigger("StateChanged");
                            uploadNext.call(this);
                        }
                    },
                    /**
					 * Stops the upload of the queued files.
					 * 
					 * @method stop
					 */
                    stop: function() {
                        if (this.state != plupload.STOPPED) {
                            this.state = plupload.STOPPED;
                            this.trigger("CancelUpload");
                            this.trigger("StateChanged");
                        }
                    },
                    /**
					 * Disables/enables browse button on request.
					 * 
					 * @method disableBrowse
					 * @param {Boolean}
					 *            disable Whether to disable or enable (default:
					 *            true)
					 */
                    disableBrowse: function() {
                        disabled = arguments[0] !== undef ? arguments[0] : true;
                        this.trigger("DisableBrowse", disabled);
                    },
                    /**
					 * Returns the specified file object by id.
					 * 
					 * @method getFile
					 * @param {String}
					 *            id File id to look for.
					 * @return {plupload.File} File object or undefined if it
					 *         wasn't found;
					 */
                    getFile: function(id) {
                        var i;
                        for (i = files.length - 1; i >= 0; i--) {
                            if (files[i].id === id) {
                                return files[i];
                            }
                        }
                    },
                    /**
					 * Removes a specific file.
					 * 
					 * @method removeFile
					 * @param {plupload.File}
					 *            file File to remove from queue.
					 */
                    removeFile: function(file) {
                        var i;
                        for (i = files.length - 1; i >= 0; i--) {
                            if (files[i].id === file.id) {
                                return this.splice(i, 1)[0];
                            }
                        }
                    },
                    /**
					 * Removes part of the queue and returns the files removed.
					 * This will also trigger the FilesRemoved and QueueChanged
					 * events.
					 * 
					 * @method splice
					 * @param {Number}
					 *            start (Optional) Start index to remove from.
					 * @param {Number}
					 *            length (Optional) Lengh of items to remove.
					 * @return {Array} Array of files that was removed.
					 */
                    splice: function(start, length) {
                        var removed;
                        // Splice and trigger events
                        removed = files.splice(start === undef ? 0 : start, length === undef ? files.length : length);
                        this.trigger("FilesRemoved", removed);
                        this.trigger("QueueChanged");
                        return removed;
                    },
                    /**
					 * Dispatches the specified event name and it's arguments to
					 * all listeners.
					 * 
					 * 
					 * @method trigger
					 * @param {String}
					 *            name Event name to fire.
					 * @param {Object..}
					 *            Multiple arguments to pass along to the
					 *            listener functions.
					 */
                    trigger: function(name) {
                        var list = events[name.toLowerCase()], i, args;
                        // console.log(name, arguments);
                        if (list) {
                            // Replace name with sender in args
                            args = Array.prototype.slice.call(arguments);
                            args[0] = this;
                            // Dispatch event to all listeners
                            for (i = 0; i < list.length; i++) {
                                // Fire event, break chain if false is returned
                                if (list[i].func.apply(list[i].scope, args) === false) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    },
                    /**
					 * Check whether uploader has any listeners to the specified
					 * event.
					 * 
					 * @method hasEventListener
					 * @param {String}
					 *            name Event name to check for.
					 */
                    hasEventListener: function(name) {
                        return !!events[name.toLowerCase()];
                    },
                    /**
					 * Adds an event listener by name.
					 * 
					 * @method bind
					 * @param {String}
					 *            name Event name to listen for.
					 * @param {function}
					 *            func Function to call ones the event gets
					 *            fired.
					 * @param {Object}
					 *            scope Optional scope to execute the specified
					 *            function in.
					 */
                    bind: function(name, func, scope) {
                        var list;
                        name = name.toLowerCase();
                        list = events[name] || [];
                        list.push({
                            func: func,
                            scope: scope || this
                        });
                        events[name] = list;
                    },
                    /**
					 * Removes the specified event listener.
					 * 
					 * @method unbind
					 * @param {String}
					 *            name Name of event to remove.
					 * @param {function}
					 *            func Function to remove from listener.
					 */
                    unbind: function(name) {
                        name = name.toLowerCase();
                        var list = events[name], i, func = arguments[1];
                        if (list) {
                            if (func !== undef) {
                                for (i = list.length - 1; i >= 0; i--) {
                                    if (list[i].func === func) {
                                        list.splice(i, 1);
                                        break;
                                    }
                                }
                            } else {
                                list = [];
                            }
                            // delete event list if it has become empty
                            if (!list.length) {
                                delete events[name];
                            }
                        }
                    },
                    /**
					 * Removes all event listeners.
					 * 
					 * @method unbindAll
					 */
                    unbindAll: function() {
                        var self = this;
                        plupload.each(events, function(list, name) {
                            self.unbind(name);
                        });
                    },
                    /**
					 * Destroys Plupload instance and cleans after itself.
					 * 
					 * @method destroy
					 */
                    destroy: function() {
                        this.stop();
                        this.trigger("Destroy");
                        // Clean-up after uploader itself
                        this.unbindAll();
                    }
                });
            };
            /**
			 * File instance.
			 * 
			 * @class plupload.File
			 * @param {String}
			 *            name Name of the file.
			 * @param {Number}
			 *            size File size.
			 */
            /**
			 * Constructs a new file instance.
			 * 
			 * @constructor
			 * @method File
			 * @param {String}
			 *            id Unique file id.
			 * @param {String}
			 *            name File name.
			 * @param {Number}
			 *            size File size in bytes.
			 */
            plupload.File = function(id, name, size) {
                var self = this;
                // Setup alias for self to reduce code size
                // when it's compressed
                /**
				 * File id this is a globally unique id for the specific file.
				 * 
				 * @property id
				 * @type String
				 */
                self.id = id;
                /**
				 * File name for example "myfile.gif".
				 * 
				 * @property name
				 * @type String
				 */
                self.name = name;
                /**
				 * File size in bytes.
				 * 
				 * @property size
				 * @type Number
				 */
                self.size = size;
                /**
				 * Number of bytes uploaded of the files total size.
				 * 
				 * @property loaded
				 * @type Number
				 */
                self.loaded = 0;
                /**
				 * Number of percentage uploaded of the file.
				 * 
				 * @property percent
				 * @type Number
				 */
                self.percent = 0;
                /**
				 * Status constant matching the plupload states QUEUED,
				 * UPLOADING, FAILED, DONE.
				 * 
				 * @property status
				 * @type Number
				 * @see plupload
				 */
                self.status = 0;
                /**
				 * add by meiqm
				 */
                self.fullpath = "";
            };
            /**
			 * Runtime class gets implemented by each upload runtime.
			 * 
			 * @class plupload.Runtime
			 * @static
			 */
            plupload.Runtime = function() {
                /**
				 * Returns a list of supported features for the runtime.
				 * 
				 * @return {Object} Name/value object with supported features.
				 */
                this.getFeatures = function() {};
                /**
				 * Initializes the upload runtime. This method should add
				 * necessary items to the DOM and register events needed for
				 * operation.
				 * 
				 * @method init
				 * @param {plupload.Uploader}
				 *            uploader Uploader instance that needs to be
				 *            initialized.
				 * @param {function}
				 *            callback Callback function to execute when the
				 *            runtime initializes or fails to initialize. If it
				 *            succeeds an object with a parameter name success
				 *            will be set to true.
				 */
                this.init = function(uploader, callback) {};
            };
            /**
			 * Runtime class gets implemented by each upload runtime.
			 * 
			 * @class plupload.QueueProgress
			 */
            /**
			 * Constructs a queue progress.
			 * 
			 * @constructor
			 * @method QueueProgress
			 */
            plupload.QueueProgress = function() {
                var self = this;
                // Setup alias for self to reduce code size
                // when it's compressed
                /**
				 * Total queue file size.
				 * 
				 * @property size
				 * @type Number
				 */
                self.size = 0;
                /**
				 * Total bytes uploaded.
				 * 
				 * @property loaded
				 * @type Number
				 */
                self.loaded = 0;
                /**
				 * Number of files uploaded.
				 * 
				 * @property uploaded
				 * @type Number
				 */
                self.uploaded = 0;
                /**
				 * Number of files failed to upload.
				 * 
				 * @property failed
				 * @type Number
				 */
                self.failed = 0;
                /**
				 * Number of files yet to be uploaded.
				 * 
				 * @property queued
				 * @type Number
				 */
                self.queued = 0;
                /**
				 * Total percent of the uploaded bytes.
				 * 
				 * @property percent
				 * @type Number
				 */
                self.percent = 0;
                /**
				 * Bytes uploaded per second.
				 * 
				 * @property bytesPerSec
				 * @type Number
				 */
                self.bytesPerSec = 0;
                /**
				 * Resets the progress to it's initial values.
				 * 
				 * @method reset
				 */
                self.reset = function() {
                    self.size = self.loaded = self.uploaded = self.failed = self.queued = self.percent = self.bytesPerSec = 0;
                };
            };
            // Create runtimes namespace
            plupload.runtimes = {};
            // Expose plupload namespace
            window.plupload = plupload;
        })();
        // 下面是flash.js的内容
        (function(f, b, d, e) {
            var a = {}, g = {};
            function c() {
                var h;
                try {
                    h = navigator.plugins["Shockwave Flash"];
                    h = h.description;
                } catch (j) {
                    try {
                        h = new ActiveXObject("ShockwaveFlash.ShockwaveFlash").GetVariable("$version");
                    } catch (i) {
                        h = "0.0";
                    }
                }
                h = h.match(/\d+/g);
                return parseFloat(h[0] + "." + h[1]);
            }
            d.flash = {
                trigger: function(j, h, i) {
                    setTimeout(function() {
                        var m = a[j], l, k;
                        if (m) {
                            m.trigger("Flash:" + h, i);
                        }
                    }, 0);
                }
            };
            d.runtimes.Flash = d.addRuntime("flash", {
                getFeatures: function() {
                    return {
                        jpgresize: true,
                        pngresize: true,
                        maxWidth: 8091,
                        maxHeight: 8091,
                        chunks: true,
                        progress: true,
                        multipart: true,
                        multi_selection: true
                    };
                },
                init: function(m, o) {
                    var k, l, h = 0, i = b.body;
                    if (c() < 10) {
                        o({
                            success: false
                        });
                        return;
                    }
                    g[m.id] = false;
                    a[m.id] = m;
                    k = b.getElementById(m.settings.browse_button);
                    l = b.createElement("div");
                    l.id = m.id + "_flash_container";
                    d.extend(l.style, {
                        position: "absolute",
                        top: "0px",
                        background: m.settings.shim_bgcolor || "transparent",
                        zIndex: 99999,
                        width: "100%",
                        height: "100%"
                    });
                    l.className = "plupload flash";
                    if (m.settings.container) {
                        i = b.getElementById(m.settings.container);
                        if (d.getStyle(i, "position") === "static") {
                            i.style.position = "relative";
                        }
                    }
                    i.appendChild(l);
                    (function() {
                        var p, q;
                        p = '<object id="' + m.id + '_flash" type="application/x-shockwave-flash" data="' + m.settings.flash_swf_url + '" ';
                        if (d.ua.ie) {
                            p += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                        }
                        p += 'width="100%" height="100%" style="outline:0"><param name="movie" value="' + m.settings.flash_swf_url + '" /><param name="flashvars" value="id=' + escape(m.id) + '" /><param name="wmode" value="transparent" /><param name="allowscriptaccess" value="always" /></object>';
                        if (d.ua.ie) {
                            q = b.createElement("div");
                            l.appendChild(q);
                            q.outerHTML = p;
                            q = null;
                        } else {
                            l.innerHTML = p;
                        }
                    })();
                    function n() {
                        return b.getElementById(m.id + "_flash");
                    }
                    function j() {
                        if (h++ > 5e3) {
                            o({
                                success: false
                            });
                            return;
                        }
                        if (g[m.id] === false) {
                            setTimeout(j, 1);
                        }
                    }
                    j();
                    k = l = null;
                    m.bind("Destroy", function(p) {
                        var q;
                        d.removeAllEvents(b.body, p.id);
                        delete g[p.id];
                        delete a[p.id];
                        q = b.getElementById(p.id + "_flash_container");
                        if (q) {
                            i.removeChild(q);
                        }
                    });
                    m.bind("Flash:Init", function() {
                        var r = {}, q;
                        try {
                            n().setFileFilters(m.settings.filters, m.settings.multi_selection);
                        } catch (p) {
                            o({
                                success: false
                            });
                            return;
                        }
                        if (g[m.id]) {
                            return;
                        }
                        g[m.id] = true;
                        m.bind("UploadFile", function(s, u) {
                            var v = s.settings, t = m.settings.resize || {};
                            n().uploadFile(r[u.id], v.url, {
                                name: u.target_name || u.name,
                                mime: d.mimeTypes[u.name.replace(/^.+\.([^.]+)/, "$1").toLowerCase()] || "application/octet-stream",
                                chunk_size: v.chunk_size,
                                width: t.width,
                                height: t.height,
                                quality: t.quality,
                                multipart: v.multipart,
                                multipart_params: v.multipart_params || {},
                                file_data_name: v.file_data_name,
                                format: /\.(jpg|jpeg)$/i.test(u.name) ? "jpg" : "png",
                                headers: v.headers,
                                urlstream_upload: v.urlstream_upload
                            });
                        });
                        m.bind("CancelUpload", function() {
                            n().cancelUpload();
                        });
                        m.bind("Flash:UploadProcess", function(t, s) {
                            var u = t.getFile(r[s.id]);
                            if (u.status != d.FAILED) {
                                u.loaded = s.loaded;
                                u.size = s.size;
                                t.trigger("UploadProgress", u);
                            }
                        });
                        m.bind("Flash:UploadChunkComplete", function(s, u) {
                            var v, t = s.getFile(r[u.id]);
                            v = {
                                chunk: u.chunk,
                                chunks: u.chunks,
                                response: u.text
                            };
                            s.trigger("ChunkUploaded", t, v);
                            if (t.status !== d.FAILED && s.state !== d.STOPPED) {
                                n().uploadNextChunk();
                            }
                            if (u.chunk == u.chunks - 1) {
                                t.status = d.DONE;
                                s.trigger("FileUploaded", t, {
                                    response: u.text
                                });
                            }
                        });
                        m.bind("Flash:SelectFiles", function(s, v) {
                            var u, t, w = [], x;
                            for (t = 0; t < v.length; t++) {
                                u = v[t];
                                x = d.guid();
                                r[x] = u.id;
                                r[u.id] = x;
                                w.push(new d.File(x, u.name, u.size));
                            }
                            if (w.length) {
                                m.trigger("FilesAdded", w);
                            }
                        });
                        m.bind("Flash:SecurityError", function(s, t) {
                            m.trigger("Error", {
                                code: d.SECURITY_ERROR,
                                message: d.translate("Security error."),
                                details: t.message,
                                file: m.getFile(r[t.id])
                            });
                        });
                        m.bind("Flash:GenericError", function(s, t) {
                            m.trigger("Error", {
                                code: d.GENERIC_ERROR,
                                message: d.translate("Generic error."),
                                details: t.message,
                                file: m.getFile(r[t.id])
                            });
                        });
                        m.bind("Flash:IOError", function(s, t) {
                            m.trigger("Error", {
                                code: d.IO_ERROR,
                                message: d.translate("IO error."),
                                details: t.message,
                                file: m.getFile(r[t.id])
                            });
                        });
                        m.bind("Flash:ImageError", function(s, t) {
                            m.trigger("Error", {
                                code: parseInt(t.code, 10),
                                message: d.translate("Image error."),
                                file: m.getFile(r[t.id])
                            });
                        });
                        m.bind("Flash:StageEvent:rollOver", function(s) {
                            var t, u;
                            t = b.getElementById(m.settings.browse_button);
                            u = s.settings.browse_button_hover;
                            if (t && u) {
                                d.addClass(t, u);
                            }
                        });
                        m.bind("Flash:StageEvent:rollOut", function(s) {
                            var t, u;
                            t = b.getElementById(m.settings.browse_button);
                            u = s.settings.browse_button_hover;
                            if (t && u) {
                                d.removeClass(t, u);
                            }
                        });
                        m.bind("Flash:StageEvent:mouseDown", function(s) {
                            var t, u;
                            t = b.getElementById(m.settings.browse_button);
                            u = s.settings.browse_button_active;
                            if (t && u) {
                                d.addClass(t, u);
                                d.addEvent(b.body, "mouseup", function() {
                                    d.removeClass(t, u);
                                }, s.id);
                            }
                        });
                        m.bind("Flash:StageEvent:mouseUp", function(s) {
                            var t, u;
                            t = b.getElementById(m.settings.browse_button);
                            u = s.settings.browse_button_active;
                            if (t && u) {
                                d.removeClass(t, u);
                            }
                        });
                        m.bind("Flash:ExifData", function(s, t) {
                            m.trigger("ExifData", m.getFile(r[t.id]), t.data);
                        });
                        m.bind("Flash:GpsData", function(s, t) {
                            m.trigger("GpsData", m.getFile(r[t.id]), t.data);
                        });
                        m.bind("QueueChanged", function(s) {
                            m.refresh();
                        });
                        m.bind("FilesRemoved", function(s, u) {
                            var t;
                            for (t = 0; t < u.length; t++) {
                                n().removeFile(r[u[t].id]);
                            }
                        });
                        m.bind("StateChanged", function(s) {
                            m.refresh();
                        });
                        m.bind("Refresh", function(s) {
                            var t, u, v;
                            n().setFileFilters(m.settings.filters, m.settings.multi_selection);
                            t = b.getElementById(s.settings.browse_button);
                            if (t) {
                                u = d.getPos(t, b.getElementById(s.settings.container));
                                v = d.getSize(t);
                                d.extend(b.getElementById(s.id + "_flash_container").style, {
                                    top: u.y + "px",
                                    left: u.x + "px",
                                    width: v.w + "px",
                                    height: v.h + "px"
                                });
                            }
                        });
                        m.bind("DisableBrowse", function(s, t) {
                            n().disableBrowse(t);
                        });
                        o({
                            success: true
                        });
                    });
                }
            });
        })(window, document, plupload);
        //		这下面是browserplus的内容
        (function(a) {
            a.runtimes.BrowserPlus = a.addRuntime("browserplus", {
                getFeatures: function() {
                    return {
                        dragdrop: true,
                        jpgresize: true,
                        pngresize: true,
                        chunks: true,
                        progress: true,
                        multipart: true,
                        multi_selection: true
                    };
                },
                init: function(g, i) {
                    var e = window.BrowserPlus, h = {}, d = g.settings, c = d.resize;
                    function f(n) {
                        var m, l, j = [], k, o;
                        for (l = 0; l < n.length; l++) {
                            k = n[l];
                            o = a.guid();
                            h[o] = k;
                            j.push(new a.File(o, k.name, k.size));
                        }
                        if (l) {
                            g.trigger("FilesAdded", j);
                        }
                    }
                    function b() {
                        var j = false;
                        g.bind("PostInit", function() {
                            var n, l = d.drop_element, p = g.id + "_droptarget", k = document.getElementById(l), m;
                            function q(s, r) {
                                e.DragAndDrop.AddDropTarget({
                                    id: s
                                }, function(t) {
                                    e.DragAndDrop.AttachCallbacks({
                                        id: s,
                                        hover: function(u) {
                                            if (!u && r) {
                                                r();
                                            }
                                        },
                                        drop: function(u) {
                                            if (r) {
                                                r();
                                            }
                                            f(u);
                                        }
                                    }, function() {});
                                });
                            }
                            function o() {
                                document.getElementById(p).style.top = "-1000px";
                            }
                            if (k) {
                                if (document.attachEvent && /MSIE/gi.test(navigator.userAgent)) {
                                    n = document.createElement("div");
                                    n.setAttribute("id", p);
                                    a.extend(n.style, {
                                        position: "absolute",
                                        top: "-1000px",
                                        background: "red",
                                        filter: "alpha(opacity=0)",
                                        opacity: 0
                                    });
                                    document.body.appendChild(n);
                                    a.addEvent(k, "dragenter", function(s) {
                                        var r, t;
                                        r = document.getElementById(l);
                                        t = a.getPos(r);
                                        a.extend(document.getElementById(p).style, {
                                            top: t.y + "px",
                                            left: t.x + "px",
                                            width: r.offsetWidth + "px",
                                            height: r.offsetHeight + "px"
                                        });
                                    });
                                    q(p, o);
                                } else {
                                    q(l);
                                }
                            }
                            a.addEvent(document.getElementById(d.browse_button), "click", function(x) {
                                var r = [], t, s, w = d.filters, v, u;
                                x.preventDefault();
                                if (j) {
                                    return;
                                }
                                no_type_restriction: for (t = 0; t < w.length; t++) {
                                    v = w[t].extensions.split(",");
                                    for (s = 0; s < v.length; s++) {
                                        if (v[s] === "*") {
                                            r = [];
                                            break no_type_restriction;
                                        }
                                        u = a.mimeTypes[v[s]];
                                        if (u && a.inArray(u, r) === -1) {
                                            r.push(a.mimeTypes[v[s]]);
                                        }
                                    }
                                }
                                e.FileBrowse.OpenBrowseDialog({
                                    mimeTypes: r
                                }, function(y) {
                                    if (y.success) {
                                        f(y.value);
                                    }
                                });
                            });
                            k = n = null;
                        });
                        g.bind("CancelUpload", function() {
                            e.Uploader.cancel({}, function() {});
                        });
                        g.bind("DisableBrowse", function(k, l) {
                            j = l;
                        });
                        g.bind("UploadFile", function(n, k) {
                            var m = h[k.id], s = {}, l = n.settings.chunk_size, o, p = [];
                            function r(t, v) {
                                var u;
                                if (k.status == a.FAILED) {
                                    return;
                                }
                                s.name = k.target_name || k.name;
                                if (l) {
                                    s.chunk = "" + t;
                                    s.chunks = "" + v;
                                }
                                u = p.shift();
                                e.Uploader.upload({
                                    url: n.settings.url,
                                    files: {
                                        file: u
                                    },
                                    cookies: document.cookies,
                                    postvars: a.extend(s, n.settings.multipart_params),
                                    progressCallback: function(y) {
                                        var x, w = 0;
                                        o[t] = parseInt(y.filePercent * u.size / 100, 10);
                                        for (x = 0; x < o.length; x++) {
                                            w += o[x];
                                        }
                                        k.loaded = w;
                                        n.trigger("UploadProgress", k);
                                    }
                                }, function(x) {
                                    var w, y;
                                    if (x.success) {
                                        w = x.value.statusCode;
                                        if (l) {
                                            n.trigger("ChunkUploaded", k, {
                                                chunk: t,
                                                chunks: v,
                                                response: x.value.body,
                                                status: w
                                            });
                                        }
                                        if (p.length > 0) {
                                            r(++t, v);
                                        } else {
                                            k.status = a.DONE;
                                            n.trigger("FileUploaded", k, {
                                                response: x.value.body,
                                                status: w
                                            });
                                            if (w >= 400) {
                                                n.trigger("Error", {
                                                    code: a.HTTP_ERROR,
                                                    message: a.translate("HTTP Error."),
                                                    file: k,
                                                    status: w
                                                });
                                            }
                                        }
                                    } else {
                                        n.trigger("Error", {
                                            code: a.GENERIC_ERROR,
                                            message: a.translate("Generic Error."),
                                            file: k,
                                            details: x.error
                                        });
                                    }
                                });
                            }
                            function q(t) {
                                k.size = t.size;
                                if (l) {
                                    e.FileAccess.chunk({
                                        file: t,
                                        chunkSize: l
                                    }, function(w) {
                                        if (w.success) {
                                            var x = w.value, u = x.length;
                                            o = Array(u);
                                            for (var v = 0; v < u; v++) {
                                                o[v] = 0;
                                                p.push(x[v]);
                                            }
                                            r(0, u);
                                        }
                                    });
                                } else {
                                    o = Array(1);
                                    p.push(t);
                                    r(0, 1);
                                }
                            }
                            if (c && /\.(png|jpg|jpeg)$/i.test(k.name)) {
                                BrowserPlus.ImageAlter.transform({
                                    file: m,
                                    quality: c.quality || 90,
                                    actions: [ {
                                        scale: {
                                            maxwidth: c.width,
                                            maxheight: c.height
                                        }
                                    } ]
                                }, function(t) {
                                    if (t.success) {
                                        q(t.value.file);
                                    }
                                });
                            } else {
                                q(m);
                            }
                        });
                        i({
                            success: true
                        });
                    }
                    if (e) {
                        e.init(function(k) {
                            var j = [ {
                                service: "Uploader",
                                version: "3"
                            }, {
                                service: "DragAndDrop",
                                version: "1"
                            }, {
                                service: "FileBrowse",
                                version: "1"
                            }, {
                                service: "FileAccess",
                                version: "2"
                            } ];
                            if (c) {
                                j.push({
                                    service: "ImageAlter",
                                    version: "4"
                                });
                            }
                            if (k.success) {
                                e.require({
                                    services: j
                                }, function(l) {
                                    if (l.success) {
                                        b();
                                    } else {
                                        i();
                                    }
                                });
                            } else {
                                i();
                            }
                        });
                    } else {
                        i();
                    }
                }
            });
        })(plupload);
        //		html4的内容
        /**
 * plupload.html4.js
 * 
 * Copyright 2010, Ryan Demmer Copyright 2009, Moxiecode Systems AB Released
 * under GPL License.
 * 
 * License: http://www.plupload.com/license Contributing:
 * http://www.plupload.com/contributing
 */
        // JSLint defined globals
        /* global plupload:false, window:false */
        (function(window, document, plupload, undef) {
            function getById(id) {
                return document.getElementById(id);
            }
            /**
			 * HTML4 implementation. This runtime has no special features it
			 * uses an form that posts files into an hidden iframe.
			 * 
			 * @static
			 * @class plupload.runtimes.Html4
			 * @extends plupload.Runtime
			 */
            plupload.runtimes.Html4 = plupload.addRuntime("html4", {
                /**
				 * Returns a list of supported features for the runtime.
				 * 
				 * @return {Object} Name/value object with supported features.
				 */
                getFeatures: function() {
                    // Only multipart feature
                    return {
                        multipart: true,
                        // WebKit and Gecko 2+ can trigger file dialog
                        // progrmmatically
                        triggerDialog: plupload.ua.gecko && window.FormData || plupload.ua.webkit
                    };
                },
                /**
				 * Initializes the upload runtime.
				 * 
				 * @method init
				 * @param {plupload.Uploader}
				 *            uploader Uploader instance that needs to be
				 *            initialized.
				 * @param {function}
				 *            callback Callback to execute when the runtime
				 *            initializes or fails to initialize. If it succeeds
				 *            an object with a parameter name success will be
				 *            set to true.
				 */
                init: function(uploader, callback) {
                    uploader.bind("Init", function(up) {
                        var container = document.body, iframe, url = "javascript", currentFile, input, currentFileId, fileIds = [], IE = /MSIE/.test(navigator.userAgent), mimes = [], filters = up.settings.filters, i, ext, type, y;
                        // Convert extensions to mime types list
                        no_type_restriction: for (i = 0; i < filters.length; i++) {
                            ext = filters[i].extensions.split(/,/);
                            for (y = 0; y < ext.length; y++) {
                                // If there's an asterisk in the list, then
                                // accept attribute is not required
                                if (ext[y] === "*") {
                                    mimes = [];
                                    break no_type_restriction;
                                }
                                type = plupload.mimeTypes[ext[y]];
                                if (type && plupload.inArray(type, mimes) === -1) {
                                    mimes.push(type);
                                }
                            }
                        }
                        mimes = mimes.join(",");
                        function createForm() {
                            var form, input, bgcolor, browseButton;
                            // Setup unique id for form
                            currentFileId = plupload.guid();
                            // Save id for Destroy handler
                            fileIds.push(currentFileId);
                            // Create form
                            form = document.createElement("form");
                            form.setAttribute("id", "form_" + currentFileId);
                            form.setAttribute("method", "post");
                            form.setAttribute("enctype", "multipart/form-data");
                            form.setAttribute("encoding", "multipart/form-data");
                            form.setAttribute("target", up.id + "_iframe");
                            form.style.position = "absolute";
                            // Create input and set attributes
                            input = document.createElement("input");
                            input.setAttribute("id", "input_" + currentFileId);
                            input.setAttribute("type", "file");
                            input.setAttribute("accept", mimes);
                            input.setAttribute("size", 1);
                            browseButton = getById(up.settings.browse_button);
                            // Route click event to input element
                            // programmatically, if possible
                            if (up.features.triggerDialog && browseButton) {
                                plupload.addEvent(getById(up.settings.browse_button), "click", function(e) {
                                    if (!input.disabled) {
                                        input.click();
                                    }
                                    e.preventDefault();
                                }, up.id);
                            }
                            // Set input styles
                            plupload.extend(input.style, {
                                width: "100%",
                                height: "100%",
                                opacity: 0,
                                fontSize: "99px",
                                // force input
                                // element to be
                                // bigger then
                                // needed to occupy
                                // whole space
                                cursor: "pointer"
                            });
                            plupload.extend(form.style, {
                                overflow: "hidden"
                            });
                            // Show the container if shim_bgcolor is specified
                            bgcolor = up.settings.shim_bgcolor;
                            if (bgcolor) {
                                form.style.background = bgcolor;
                            }
                            // no opacity in IE
                            if (IE) {
                                plupload.extend(input.style, {
                                    filter: "alpha(opacity=0)"
                                });
                            }
                            // add change event
                            plupload.addEvent(input, "change", function(e) {
                                var element = e.target, name, files = [], topElement;
                                if (element.value) {
                                    getById("form_" + currentFileId).style.top = -1048575 + "px";
                                    // Get file name
                                    name = element.value.replace(/\\/g, "/");
                                    // get fullpath
                                    var fullpath = name;
                                    name = name.substring(name.length, name.lastIndexOf("/") + 1);
                                    // Push files
                                    var file = new plupload.File(currentFileId, name);
                                    file.fullpath = fullpath;
                                    files.push(file);
                                    // Clean-up events - they won't be needed
                                    // anymore
                                    if (!up.features.triggerDialog) {
                                        plupload.removeAllEvents(form, up.id);
                                    } else {
                                        plupload.removeEvent(browseButton, "click", up.id);
                                    }
                                    plupload.removeEvent(input, "change", up.id);
                                    // Create and position next form
                                    createForm();
                                    // Fire FilesAdded event
                                    if (files.length) {
                                        uploader.trigger("FilesAdded", files);
                                    }
                                }
                            }, up.id);
                            // append to container
                            form.appendChild(input);
                            container.appendChild(form);
                            up.refresh();
                        }
                        /**
						 * add by meiqm get file size
						 */
                        function getFileSize(el) {
                            var size;
                            if (window.ActiveXObject) {
                                if (document.all) {
                                    window.oldOnError = window.onerror;
                                    window.onerror = function(err) {
                                        if (null != err && err.indexOf("automation") != -1) {
                                            alert("no access to the file permission");
                                            return true;
                                        } else return false;
                                    };
                                    var fso = new ActiveXObject("Scripting.FileSystemObject");
                                    var file = fso.GetFile(el.name);
                                    size = file.Size;
                                }
                            } else if (navigator.userAgent.indexOf("Firefox") != -1 && el.files.length > 0) {
                                size = el.files[0].fileSize;
                            }
                            return size;
                        }
                        function createIframe() {
                            var temp = document.createElement("div");
                            // Create iframe using a temp div since IE 6 won't
                            // be able to set the name using setAttribute or
                            // iframe.name
                            temp.innerHTML = '<iframe id="' + up.id + '_iframe" name="' + up.id + '_iframe" src="' + url + ':&quot;&quot;" style="display:none"></iframe>';
                            iframe = temp.firstChild;
                            container.appendChild(iframe);
                            // Add IFrame onload event
                            plupload.addEvent(iframe, "load", function(e) {
                                var n = e.target, el, result;
                                // Ignore load event if there is no file
                                if (!currentFile) {
                                    return;
                                }
                                try {
                                    el = n.contentWindow.document || n.contentDocument || window.frames[n.id].document;
                                } catch (ex) {
                                    // Probably a permission denied error
                                    up.trigger("Error", {
                                        code: plupload.SECURITY_ERROR,
                                        message: plupload.translate("Security error."),
                                        file: currentFile
                                    });
                                    return;
                                }
                                // Get result
                                result = el.body.innerHTML;
                                // Assume no error
                                if (result) {
                                    currentFile.status = plupload.DONE;
                                    currentFile.loaded = 1025;
                                    currentFile.percent = 100;
                                    up.trigger("UploadProgress", currentFile);
                                    up.trigger("FileUploaded", currentFile, {
                                        response: result
                                    });
                                }
                            }, up.id);
                        }
                        // end createIframe
                        if (up.settings.container) {
                            container = getById(up.settings.container);
                            if (plupload.getStyle(container, "position") === "static") {
                                container.style.position = "relative";
                            }
                        }
                        // Upload file
                        up.bind("UploadFile", function(up, file) {
                            var form, input;
                            // File upload finished
                            if (file.status == plupload.DONE || file.status == plupload.FAILED || up.state == plupload.STOPPED) {
                                return;
                            }
                            // Get the form and input elements
                            form = getById("form_" + file.id);
                            input = getById("input_" + file.id);
                            // Set input element name attribute which allows it
                            // to be submitted
                            input.setAttribute("name", up.settings.file_data_name);
                            // Store action
                            form.setAttribute("action", up.settings.url);
                            // Append multipart parameters
                            plupload.each(plupload.extend({
                                name: file.target_name || file.name
                            }, up.settings.multipart_params), function(value, name) {
                                var hidden = document.createElement("input");
                                plupload.extend(hidden, {
                                    type: "hidden",
                                    name: name,
                                    value: value
                                });
                                form.insertBefore(hidden, form.firstChild);
                            });
                            currentFile = file;
                            // Hide the current form
                            getById("form_" + currentFileId).style.top = -1048575 + "px";
                            form.submit();
                        });
                        up.bind("FileUploaded", function(up) {
                            up.refresh();
                        });
                        up.bind("StateChanged", function(up) {
                            if (up.state == plupload.STARTED) {
                                createIframe();
                            } else if (up.state == plupload.STOPPED) {
                                window.setTimeout(function() {
                                    plupload.removeEvent(iframe, "load", up.id);
                                    if (iframe.parentNode) {
                                        // #382
                                        iframe.parentNode.removeChild(iframe);
                                    }
                                }, 0);
                            }
                            plupload.each(up.files, function(file, i) {
                                if (file.status === plupload.DONE || file.status === plupload.FAILED) {
                                    var form = getById("form_" + file.id);
                                    if (form) {
                                        form.parentNode.removeChild(form);
                                    }
                                }
                            });
                        });
                        // Refresh button, will reposition the input form
                        up.bind("Refresh", function(up) {
                            var browseButton, topElement, hoverClass, activeClass, browsePos, browseSize, inputContainer, inputFile, zIndex;
                            browseButton = getById(up.settings.browse_button);
                            if (browseButton) {
                                browsePos = plupload.getPos(browseButton, getById(up.settings.container));
                                browseSize = plupload.getSize(browseButton);
                                inputContainer = getById("form_" + currentFileId);
                                inputFile = getById("input_" + currentFileId);
                                plupload.extend(inputContainer.style, {
                                    top: browsePos.y + "px",
                                    left: browsePos.x + "px",
                                    width: browseSize.w + "px",
                                    height: browseSize.h + "px"
                                });
                                // for IE and WebKit place input element
                                // underneath the browse button and route
                                // onclick event
                                // TODO: revise when browser support for this
                                // feature will change
                                if (up.features.triggerDialog) {
                                    if (plupload.getStyle(browseButton, "position") === "static") {
                                        plupload.extend(browseButton.style, {
                                            position: "relative"
                                        });
                                    }
                                    zIndex = parseInt(browseButton.style.zIndex, 10);
                                    if (isNaN(zIndex)) {
                                        zIndex = 0;
                                    }
                                    plupload.extend(browseButton.style, {
                                        zIndex: zIndex
                                    });
                                    plupload.extend(inputContainer.style, {
                                        zIndex: zIndex - 1
                                    });
                                }
                                /*
								 * Since we have to place input[type=file] on
								 * top of the browse_button for some browsers
								 * (FF, Opera), browse_button loses
								 * interactivity, here we try to neutralize this
								 * issue highlighting browse_button with a
								 * special class TODO: needs to be revised as
								 * things will change
								 */
                                hoverClass = up.settings.browse_button_hover;
                                activeClass = up.settings.browse_button_active;
                                topElement = up.features.triggerDialog ? browseButton : inputContainer;
                                if (hoverClass) {
                                    plupload.addEvent(topElement, "mouseover", function() {
                                        plupload.addClass(browseButton, hoverClass);
                                    }, up.id);
                                    plupload.addEvent(topElement, "mouseout", function() {
                                        plupload.removeClass(browseButton, hoverClass);
                                    }, up.id);
                                }
                                if (activeClass) {
                                    plupload.addEvent(topElement, "mousedown", function() {
                                        plupload.addClass(browseButton, activeClass);
                                    }, up.id);
                                    plupload.addEvent(document.body, "mouseup", function() {
                                        plupload.removeClass(browseButton, activeClass);
                                    }, up.id);
                                }
                            }
                        });
                        // Remove files
                        uploader.bind("FilesRemoved", function(up, files) {
                            var i, n;
                            for (i = 0; i < files.length; i++) {
                                n = getById("form_" + files[i].id);
                                if (n) {
                                    n.parentNode.removeChild(n);
                                }
                            }
                        });
                        uploader.bind("DisableBrowse", function(up, disabled) {
                            var input = document.getElementById("input_" + currentFileId);
                            if (input) {
                                input.disabled = disabled;
                            }
                        });
                        // Completely destroy the runtime
                        uploader.bind("Destroy", function(up) {
                            var name, element, form, elements = {
                                inputContainer: "form_" + currentFileId,
                                inputFile: "input_" + currentFileId,
                                browseButton: up.settings.browse_button
                            };
                            // Unbind event handlers
                            for (name in elements) {
                                element = getById(elements[name]);
                                if (element) {
                                    plupload.removeAllEvents(element, up.id);
                                }
                            }
                            plupload.removeAllEvents(document.body, up.id);
                            // Remove mark-up
                            plupload.each(fileIds, function(id, i) {
                                form = getById("form_" + id);
                                if (form) {
                                    container.removeChild(form);
                                }
                            });
                        });
                        // Create initial form
                        createForm();
                    });
                    callback({
                        success: true
                    });
                }
            });
        })(window, document, plupload);
        //		zh-cn的内容
        plupload.addI18n({
            "Select files": "选择文件",
            "Add files to the upload queue and click the start button.": "添加文件到上传队列，然后点击开始上传按钮。",
            Filename: "文件名",
            Status: "状态",
            Size: "大小",
            "Add Files": "添加文件",
            "Stop Upload": "停止上传",
            "Start Upload": "开始上传",
            "Add files": "添加文件",
            "Add files.": "添加文件。",
            "Stop current upload": "停止当前上传",
            "Start uploading queue": "开始上传队列",
            "Stop upload": "停止上传",
            "Start upload": "开始上传",
            "Uploaded %d/%d files": "已经上传 %d/%d 个文件",
            "N/A": "N/A",
            "Drag files here.": "拖动文件到这里",
            "File extension error.": "文件扩展名错误",
            "File size error.": "文件大小错误",
            "File count error.": "文件数量错误",
            "Init error.": "初始化错误",
            "HTTP Error.": "HTTP 错误",
            "Security error.": "安全错误",
            "Generic error.": "常见错误",
            "IO error.": "IO错误",
            "File: %s": "文件百分比 : %s",
            Close: "关闭",
            "%d files queued": "%d 个队列中",
            "Using runtime: ": "使用运行时: ",
            "File: %f, size: %s, max file size: %m": "文件名: %f, 大小: %s, 文件最大值: %m",
            "Upload element accepts only %d file(s) at a time. Extra files were stripped.": "上传文件只接受%d，其他的文件被忽略",
            "Upload URL might be wrong or doesn't exist": "上传文件有错误或者文件不存在",
            "Error: File too large: ": "错误：文件太大: ",
            "Error: Invalid file extension: ": "错误：禁止的文件扩展名: "
        });
        return jQuery.noConflict(true);
    };
});

define("js/jquery.plupload.queue-debug", [ "./plupload-debug", "../../theme/default/css/jquery.plupload.queue.css" ], function(require, exports, module) {
    return function(jQuery) {
        var jQuery = require("./plupload-debug")(jQuery);
        /**
 * jquery.plupload.queue.js
 * 
 * Copyright 2009, Moxiecode Systems AB Released under GPL License.
 * 
 * License: http://www.plupload.com/license Contributing:
 * http://www.plupload.com/contributing
 */
        // JSLint defined globals
        /* global plupload:false, jQuery:false, alert:false */
        require("../theme/default/css/jquery.plupload.queue.css");
        (function($) {
            var uploaders = {};
            function _(str) {
                return plupload.translate(str) || str;
            }
            function renderUI(id, target) {
                // Remove all existing non plupload items
                target.contents().each(function(i, node) {
                    node = $(node);
                    if (!node.is(".plupload")) {
                        node.remove();
                    }
                });
                target.prepend('<div class="plupload_wrapper plupload_scroll">' + '<div id="' + id + '_container" class="plupload_container">' + '<div class="plupload">' + '<div class="plupload_header">' + '<div class="plupload_header_content">' + '<div class="plupload_header_title">' + _("Select files") + "</div>" + '<div class="plupload_header_text">' + _("Add files to the upload queue and click the start button.") + "</div>" + "</div>" + "</div>" + '<div class="plupload_content">' + '<div class="plupload_filelist_header">' + '<div class="plupload_file_name">' + _("Filename") + "</div>" + '<div class="plupload_file_action">&nbsp;</div>' + '<div class="plupload_file_status"><span>' + _("Status") + "</span></div>" + '<div class="plupload_file_size">' + _("Size") + "</div>" + '<div class="plupload_clearer">&nbsp;</div>' + "</div>" + '<ul id="' + id + '_filelist" class="plupload_filelist"></ul>' + '<div class="plupload_filelist_footer">' + '<div class="plupload_file_name">' + '<div class="plupload_buttons">' + '<a href="#" class="plupload_button plupload_add">' + _("Add files") + "</a>" + '<a href="#" class="plupload_button plupload_start">' + _("Start upload") + "</a>" + "</div>" + '<span class="plupload_upload_status"></span>' + "</div>" + '<div class="plupload_file_action"></div>' + '<div class="plupload_file_status"><span class="plupload_total_status">0%</span></div>' + '<div class="plupload_file_size"><span class="plupload_total_file_size">0 b</span></div>' + '<div class="plupload_progress">' + '<div class="plupload_progress_container">' + '<div class="plupload_progress_bar"></div>' + "</div>" + "</div>" + '<div class="plupload_clearer">&nbsp;</div>' + "</div>" + "</div>" + "</div>" + "</div>" + '<input type="hidden" id="' + id + '_count" name="' + id + '_count" value="0" />' + "</div>");
            }
            $.fn.pluploadQueue = function(settings) {
                if (settings) {
                    this.each(function() {
                        var uploader, target, id;
                        target = $(this);
                        id = target.attr("id");
                        if (!id) {
                            id = plupload.guid();
                            target.attr("id", id);
                        }
                        uploader = new plupload.Uploader($.extend({
                            dragdrop: true,
                            container: id
                        }, settings));
                        uploaders[id] = uploader;
                        function handleStatus(file) {
                            var actionClass;
                            if (file.status == plupload.DONE) {
                                actionClass = "plupload_done";
                            }
                            if (file.status == plupload.FAILED) {
                                actionClass = "plupload_failed";
                            }
                            if (file.status == plupload.QUEUED) {
                                actionClass = "plupload_delete";
                            }
                            if (file.status == plupload.UPLOADING) {
                                actionClass = "plupload_uploading";
                            }
                            var icon = $("#" + file.id).attr("class", actionClass).find("a").css("display", "block");
                            if (file.hint) {
                                icon.attr("title", file.hint);
                            }
                        }
                        function updateTotalProgress() {
                            $("span.plupload_total_status", target).html(uploader.total.percent + "%");
                            $("div.plupload_progress_bar", target).css("width", uploader.total.percent + "%");
                            $("span.plupload_upload_status", target).text(_("Uploaded %d/%d files").replace(/%d\/%d/, uploader.total.uploaded + "/" + uploader.files.length));
                        }
                        function updateList() {
                            var fileList = $("ul.plupload_filelist", target).html(""), inputCount = 0, inputHTML;
                            $.each(uploader.files, function(i, file) {
                                inputHTML = "";
                                if (file.status == plupload.DONE) {
                                    if (file.target_name) {
                                        inputHTML += '<input type="hidden" name="' + id + "_" + inputCount + '_tmpname" value="' + plupload.xmlEncode(file.target_name) + '" />';
                                    }
                                    inputHTML += '<input type="hidden" name="' + id + "_" + inputCount + '_name" value="' + plupload.xmlEncode(file.name) + '" />';
                                    inputHTML += '<input type="hidden" name="' + id + "_" + inputCount + '_status" value="' + (file.status == plupload.DONE ? "done" : "failed") + '" />';
                                    inputCount++;
                                    $("#" + id + "_count").val(inputCount);
                                }
                                fileList.append('<li id="' + file.id + '">' + '<div class="plupload_file_name"><span>' + file.name + "</span></div>" + '<div class="plupload_file_action"><a href="#"></a></div>' + '<div class="plupload_file_status">' + file.percent + "%</div>" + '<div class="plupload_file_size">' + plupload.formatSize(file.size) + "</div>" + '<div class="plupload_clearer">&nbsp;</div>' + inputHTML + "</li>");
                                handleStatus(file);
                                $("#" + file.id + ".plupload_delete a").click(function(e) {
                                    $("#" + file.id).remove();
                                    uploader.removeFile(file);
                                    e.preventDefault();
                                });
                            });
                            $("span.plupload_total_file_size", target).html(plupload.formatSize(uploader.total.size));
                            if (uploader.total.queued === 0) {
                                $("span.plupload_add_text", target).text(_("Add files."));
                            } else {
                                $("span.plupload_add_text", target).text(uploader.total.queued + " files queued.");
                            }
                            $("a.plupload_start", target).toggleClass("plupload_disabled", uploader.files.length == uploader.total.uploaded + uploader.total.failed);
                            // Scroll to end of file list
                            fileList[0].scrollTop = fileList[0].scrollHeight;
                            updateTotalProgress();
                            // Re-add drag message if there is no files
                            if (!uploader.files.length && uploader.features.dragdrop && uploader.settings.dragdrop) {
                                $("#" + id + "_filelist").append('<li class="plupload_droptext">' + _("Drag files here.") + "</li>");
                            }
                        }
                        uploader.bind("UploadFile", function(up, file) {
                            $("#" + file.id).addClass("plupload_current_file");
                        });
                        uploader.bind("Init", function(up, res) {
                            renderUI(id, target);
                            // Enable rename support
                            if (!settings.unique_names && settings.rename) {
                                $("#" + id + "_filelist div.plupload_file_name span", target).live("click", function(e) {
                                    var targetSpan = $(e.target), file, parts, name, ext = "";
                                    // Get file name and split out name and
                                    // extension
                                    file = up.getFile(targetSpan.parents("li")[0].id);
                                    name = file.name;
                                    parts = /^(.+)(\.[^.]+)$/.exec(name);
                                    if (parts) {
                                        name = parts[1];
                                        ext = parts[2];
                                    }
                                    // Display input element
                                    targetSpan.hide().after('<input type="text" />');
                                    targetSpan.next().val(name).focus().blur(function() {
                                        targetSpan.show().next().remove();
                                    }).keydown(function(e) {
                                        var targetInput = $(this);
                                        if (e.keyCode == 13) {
                                            e.preventDefault();
                                            // Rename file and glue extension
                                            // back on
                                            file.name = targetInput.val() + ext;
                                            targetSpan.text(file.name);
                                            targetInput.blur();
                                        }
                                    });
                                });
                            }
                            $("a.plupload_add", target).attr("id", id + "_browse");
                            up.settings.browse_button = id + "_browse";
                            // Enable drag/drop
                            if (up.features.dragdrop && up.settings.dragdrop) {
                                up.settings.drop_element = id + "_filelist";
                                $("#" + id + "_filelist").append('<li class="plupload_droptext">' + _("Drag files here.") + "</li>");
                            }
                            $("#" + id + "_container").attr("title", "Using runtime: " + res.runtime);
                            $("a.plupload_start", target).click(function(e) {
                                if (!$(this).hasClass("plupload_disabled")) {
                                    uploader.start();
                                }
                                e.preventDefault();
                            });
                            $("a.plupload_stop", target).click(function(e) {
                                e.preventDefault();
                                uploader.stop();
                            });
                            $("a.plupload_start", target).addClass("plupload_disabled");
                        });
                        uploader.init();
                        uploader.bind("Error", function(up, err) {
                            var file = err.file, message;
                            if (file) {
                                message = err.message;
                                if (err.details) {
                                    message += " (" + err.details + ")";
                                }
                                if (err.code == plupload.FILE_SIZE_ERROR) {
                                    alert(_("Error: File too large: ") + file.name);
                                }
                                if (err.code == plupload.FILE_EXTENSION_ERROR) {
                                    alert(_("Error: Invalid file extension: ") + file.name);
                                }
                                file.hint = message;
                                $("#" + file.id).attr("class", "plupload_failed").find("a").css("display", "block").attr("title", message);
                            }
                        });
                        uploader.bind("StateChanged", function() {
                            if (uploader.state === plupload.STARTED) {
                                $("li.plupload_delete a,div.plupload_buttons", target).hide();
                                $("span.plupload_upload_status,div.plupload_progress,a.plupload_stop", target).css("display", "block");
                                $("span.plupload_upload_status", target).text("Uploaded " + uploader.total.uploaded + "/" + uploader.files.length + " files");
                                if (settings.multiple_queues) {
                                    $("span.plupload_total_status,span.plupload_total_file_size", target).show();
                                }
                            } else {
                                updateList();
                                $("a.plupload_stop,div.plupload_progress", target).hide();
                                $("a.plupload_delete", target).css("display", "block");
                            }
                        });
                        uploader.bind("QueueChanged", updateList);
                        uploader.bind("FileUploaded", function(up, file) {
                            handleStatus(file);
                        });
                        uploader.bind("UploadProgress", function(up, file) {
                            // Set file specific progress
                            $("#" + file.id + " div.plupload_file_status", target).html(file.percent + "%");
                            handleStatus(file);
                            updateTotalProgress();
                            if (settings.multiple_queues && uploader.total.uploaded + uploader.total.failed == uploader.files.length) {
                                $(".plupload_buttons,.plupload_upload_status", target).css("display", "inline");
                                $(".plupload_start", target).addClass("plupload_disabled");
                                $("span.plupload_total_status,span.plupload_total_file_size", target).hide();
                            }
                        });
                        // Call setup function
                        if (settings.setup) {
                            settings.setup(uploader);
                        }
                    });
                    return this;
                } else {
                    // Get uploader instance for specified element
                    return uploaders[$(this[0]).attr("id")];
                }
            };
        })(jQuery);
        return jQuery.noConflict(true);
    };
});