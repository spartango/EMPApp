/*
Lightbox v2.51i
by Lokesh Dhakar - http://www.lokeshdhakar.com

For more information, visit:
http://lokeshdhakar.com/projects/lightbox2/

Licensed under the Creative Commons Attribution 2.5 License - http://creativecommons.org/licenses/by/2.5/
- free for use in both personal and commercial projects
- attribution requires leaving author name, author link, and the license info intact
	
Thanks
- Scott Upton(uptonic.com), Peter-Paul Koch(quirksmode.com), and Thomas Fuchs(mir.aculo.us) for ideas, libs, and snippets.
- Artemy Tregubenko (arty.name) for cleanup and help in updating to latest proto-aculous in v2.05.
- Mihaly Szalai (dragonzap.szunyi.com), automatic image resize for screen, fullscreen viewer and print button.

Table of Contents
=================
LightboxOptions

Lightbox
- constructor
- init
- enable
- build
- start
- supportFullScreen
- changeImage
- sizeContainer
- getImageSize
- showImage
- fullScreen
- setFullScreen
- updateNav
- updateDetails
- preloadNeigbhoringImages
- enableKeyboardNav
- disableKeyboardNav
- keyboardAction
- print
- end

options = new LightboxOptions
lightbox = new Lightbox options
*/
(function () {
	var $ = jQuery, Lightbox, LightboxOptions, $lightbox, $image, _t;
	var winW = 800,	winH = 600, imgW, imgH, imgoW, imgoH, isfull = false;
	var pfx = ["webkit", "moz", "ms", "o", ""];
	LightboxOptions = (function () {
		function LightboxOptions() {
			this.fileLoadingImage = 'images/loading.gif';
			this.fileCloseImage = 'images/close.png';
			this.resizeDuration = 700;
			this.fadeDuration = 800;
			this.labelImage = "Image";
			this.labelOf = "of";
			this.fulls = true;
			this.print = false;
		}
		return LightboxOptions;
	})();
	Lightbox = (function () {
		function Lightbox(options) {
			this.options = options;
			this.album = [];
			this.currentImageIndex = void 0;
			this.init();
		};
		Lightbox.prototype.init = function () {
			this.enable();
			return this.build();
		};
		Lightbox.prototype.enable = function () {
			return $('body').on('click', 'a[rel^=lightbox], area[rel^=lightbox]', function (e) {
				_t.start($(e.currentTarget));
				return false;
			});
		};
		Lightbox.prototype.build = function () {
			_t = this;
			$("<div>", {
				id: 'lightboxOverlay'
			}).after($('<div/>', {
				id: 'lightbox'
			}).append($('<div/>', {
				"class": 'lb-outerContainer'
			}).append($('<div/>', {
				"class": 'lb-container'
			}).append($('<img/>', {
				"class": 'lb-image'
			}), $('<div/>', {
				"class": 'lb-nav'
			}).append($('<a/>', {
				"class": 'lb-prev'
			}), $('<a/>', {
				"class": 'lb-next'
			})), $('<div/>', {
				"class": 'lb-loader'
			}).append($('<a/>', {
				"class": 'lb-cancel'
			}).append($('<img/>', {
				src: this.options.fileLoadingImage
			}))))), $('<div/>', {
				"class": 'lb-dataContainer'
			}).append($('<div/>', {
				"class": 'lb-data'
			}).append($('<div/>', {
				"class": 'lb-details'
			}).append($('<span/>', {
				"class": 'lb-caption'
			}), $('<span/>', {
				"class": 'lb-number'
			})), $('<div/>', {
				"class": 'lb-closeContainer'
			}).append($('<a/>', {
				"class": 'lb-close'
			}).append($('<img/>', {
				src: this.options.fileCloseImage
			}))))))).appendTo($('body'));
			$lightbox = $('#lightbox');
			$image = $lightbox.find('.lb-image');
			$('#lightboxOverlay').hide().on('click', function (e) {
				_t.end();
				return false;
			});
			if (this.options.fulls)
			{
				$('<div/>', {'class': 'lb-fullScreen'}).appendTo('.lb-container');
				$lightbox.find('.lb-fullScreen').on('click', _t.fullScreen);
			}
			if (this.options.print)
			{
				$('<div/>', {'class': 'lb-print'}).appendTo('.lb-closeContainer');
				$lightbox.find('.lb-print').on('click', _t.print);
			}
			$lightbox.hide().on('click', function (e) {
				if ($(e.target).attr('id') === 'lightbox') _t.end();
				return false;
			});
			$lightbox.find('.lb-outerContainer').on('click', function (e) {
				if ($(e.target).attr('id') === 'lightbox') _t.end();
				return false;
			});
			$lightbox.find('.lb-prev').on('click', function (e) {
				_t.changeImage(_t.currentImageIndex - 1);
				return false;
			});
			$lightbox.find('.lb-next').on('click', function (e) {
				_t.changeImage(_t.currentImageIndex + 1);
				return false;
			});
			$lightbox.find('.lb-loader, .lb-close').on('click', function (e) {
				_t.end();
				return false;
			});
		};
		Lightbox.prototype.start = function ($link) {
			var a, i, imageNumber, _len, _ref;
			this.supportFullScreen();
			this.sizeOverlay();
			$(window).on("resize", this.sizeOverlay);
			$('select, object, embed').css('visibility', 'hidden');
			$('#lightboxOverlay').fadeIn(this.options.fadeDuration);
			this.album = [];
			imageNumber = 0;
			if ($link.attr('rel') === 'lightbox') {
				this.album.push({
					link: $link.attr('href'),
					title: $link.attr('title')
				});
			} else {
				_ref = $($link.prop("tagName") + '[rel="' + $link.attr('rel') + '"]');
				for (i = 0, _len = _ref.length; i < _len; i++) {
					a = _ref[i];
					this.album.push({
						link: $(a).attr('href'),
						title: $(a).attr('title')
					});
					if ($(a).attr('href') === $link.attr('href')) imageNumber = i;
				}
			}					
			$lightbox.fadeIn(this.options.fadeDuration);
			this.changeImage(imageNumber);
		};
		Lightbox.prototype.supportFullScreen = function() {
			if (pfx.length != 5) return false;
			var pfx0 = ["IsFullScreen", "FullScreen"];
			var pfx1 = ["CancelFullScreen", "RequestFullScreen"];
			var p = 0, k, m, t = "undefined";
			while (p < pfx.length && !document[m]) {
				k = 0;
				while (k < pfx0.length) {
					m = pfx0[k];
					if (pfx[p] == "") {
						m = m.substr(0, 1).toLowerCase() + m.substr(1);
						pfx1[0] = pfx1[0].substr(0, 1).toLowerCase() + pfx1[0].substr(1);
						pfx1[1] = pfx1[1].substr(0, 1).toLowerCase() + pfx1[1].substr(1);
					}
					m = pfx[p] + m;
					t = typeof document[m];
					if (t != "undefined") {
						pfx = [pfx[p]+pfx1[0], pfx[p]+pfx1[1]];
						isfull = (t == "function" ? document[m]() : document[m]);
						return true;
					}
					k++;
				}
				p++;
			}
			pfx = false;
			return t;
		};
		Lightbox.prototype.changeImage = function (imageNumber) {
			this.disableKeyboardNav();
			$('#lightboxOverlay').fadeIn(this.options.fadeDuration);
			$lightbox.find('.lb-loader').fadeIn('slow');
			$lightbox.find('.lb-image, .lb-nav, .lb-prev, .lb-next, .lb-dataContainer, .lb-numbers, .lb-caption').hide();
			$lightbox.find('.lb-outerContainer').addClass('animating');
			var preloader = new Image;
			preloader.onload = function () {
				$image.attr('src', _t.album[imageNumber].link);
				imgoW = preloader.width;
				imgoH = preloader.height;
				return _t.getImageSize();
			};
			preloader.src = this.album[imageNumber].link;
			this.currentImageIndex = imageNumber;
		};
		Lightbox.prototype.sizeOverlay = function () {
			if (!isfull || pfx == false) {
				if (window.innerWidth && window.innerHeight) {
					winW = window.innerWidth;
					winH = window.innerHeight;
				} else if (document.compatMode == 'CSS1Compat' && document.documentElement && document.documentElement.offsetWidth) {
					winW = document.documentElement.offsetWidth;
					winH = document.documentElement.offsetHeight;
				} else if (document.body && document.body.offsetWidth) {
					winW = document.body.offsetWidth;
					winH = document.body.offsetHeight;
				}
			}
			return $('#lightboxOverlay').width(winW).height(winH);
		};
		Lightbox.prototype.getImageSize = function () {
			var wW = winW, wH = winH;
			if (isfull) {
				if (pfx != false) {
					wW = screen.width;
					wH = screen.height;
				}
			}
			else {
				wW *= 0.9;
				wH *= 0.8;
			}
			imgW = imgoW;
			imgH = imgoH;
			if (imgW > wW) {
				imgH = (wW * imgH) / imgW;
				imgW = wW;
			}
			if (imgH > wH) {
				imgW = (wH * imgW) / imgH;
				imgH = wH;
			}
			if (isfull) {
				var imgM = (wH - imgH) / 2;
				if (imgM < 0) imgM = 0;
				$lightbox.find('.lb-image').css('margin', imgM + 'px auto');
			}
			this.sizeContainer(imgW, imgH);
		}
		Lightbox.prototype.sizeContainer = function (imageWidth, imageHeight) {
			var $container, $outerContainer, containerBottomPadding, containerLeftPadding, containerRightPadding, containerTopPadding, newHeight, newWidth, oldHeight, oldWidth;
			$outerContainer = $lightbox.find('.lb-outerContainer');
			if (isfull) return _t.showImage();
			$image.css('margin', '0');
			oldWidth = $outerContainer.outerWidth();
			oldHeight = $outerContainer.outerHeight();
			$container = $lightbox.find('.lb-container');
			containerTopPadding = parseInt($container.css('padding-top'), 10);
			containerRightPadding = parseInt($container.css('padding-right'), 10);
			containerBottomPadding = parseInt($container.css('padding-bottom'), 10);
			containerLeftPadding = parseInt($container.css('padding-left'), 10);
			newWidth = imageWidth + containerLeftPadding + containerRightPadding;
			newHeight = imageHeight + containerTopPadding + containerBottomPadding;
			if (newWidth !== oldWidth && newHeight !== oldHeight) {
				$outerContainer.animate({
					width: newWidth,
						height: newHeight
				}, this.options.resizeDuration, 'swing');
			} else if (newWidth !== oldWidth) {
				$outerContainer.animate({
					width: newWidth
				}, this.options.resizeDuration, 'swing');
			} else if (newHeight !== oldHeight) {
				$outerContainer.animate({
					height: newHeight
				}, this.options.resizeDuration, 'swing');
			}
			setTimeout(function () {
				$lightbox.find('.lb-dataContainer').width(newWidth);
				$lightbox.find('.lb-prevLink').height(newHeight);
				$lightbox.find('.lb-nextLink').height(newHeight);
				_t.showImage();
			}, this.options.resizeDuration);
		};
		Lightbox.prototype.showImage = function () {
			$lightbox.find('.lb-loader').hide();
			$image.fadeIn('slow');
			this.updateNav();
			this.updateDetails();
			this.preloadNeighboringImages();
			this.enableKeyboardNav();
		};
		Lightbox.prototype.fullScreen = function () {
			if (isfull) {
				$image.hide();
				$lightbox.find('.lb-dataContainer').hide();
				_t.setFullScreen(document, false);
				$lightbox.find('.lb-outerContainer').attr('style', '');
			} else {
				_t.setFullScreen(document.getElementById("lightbox"), true);
				$lightbox.find('.lb-outerContainer').attr('style', 'height: 100%;');
				$lightbox.find('.lb-dataContainer').attr('style', 'width: 100%');
			}
			_t.getImageSize();
		};
		Lightbox.prototype.setFullScreen = function(obj, id) {
			isfull = id;
			if (id) {
				$('#lightbox').attr("class", "full-screen");
				if (pfx != false) obj[pfx[1]]();
			} else {
				$('#lightbox').attr("class", "");
				if (pfx != false) obj[pfx[0]]();
			}
		};
		Lightbox.prototype.updateNav = function () {
			$lightbox.find('.lb-nav').show();
			if (this.currentImageIndex > 0) $lightbox.find('.lb-prev').show();
			if (this.currentImageIndex < this.album.length - 1)
				$lightbox.find('.lb-next').show();
		};
		Lightbox.prototype.updateDetails = function () {
			if (typeof this.album[this.currentImageIndex].title !== 'undefined' && this.album[this.currentImageIndex].title !== "")
				$lightbox.find('.lb-caption').html(this.album[this.currentImageIndex].title).fadeIn('fast');
				
			if (this.album.length > 1)
				$lightbox.find('.lb-number').html(this.options.labelImage + ' ' + (this.currentImageIndex + 1) + ' ' + this.options.labelOf + '  ' + this.album.length).fadeIn('fast');
			else
				$lightbox.find('.lb-number').hide();
			$lightbox.find('.lb-outerContainer').removeClass('animating');
			$lightbox.find('.lb-dataContainer').fadeIn(this.resizeDuration, function () {
				return _t.sizeOverlay();
			});
		};
		Lightbox.prototype.preloadNeighboringImages = function () {
			var preloadNext, preloadPrev;
			if (this.album.length > this.currentImageIndex + 1) {
				preloadNext = new Image;
				preloadNext.src = this.album[this.currentImageIndex + 1].link;
			}
			if (this.currentImageIndex > 0) {
				preloadPrev = new Image;
				preloadPrev.src = this.album[this.currentImageIndex - 1].link;
			}
		};
		Lightbox.prototype.enableKeyboardNav = function () {
			$(document).on('keyup.keyboard', $.proxy(this.keyboardAction, this));
		};
		Lightbox.prototype.disableKeyboardNav = function () {
			$(document).off('.keyboard');
		};
		Lightbox.prototype.keyboardAction = function (event) {
			var KEYCODE_ESC, KEYCODE_LEFTARROW, KEYCODE_RIGHTARROW, key, keycode;
			KEYCODE_ESC = 27;
			KEYCODE_LEFTARROW = 37;
			KEYCODE_RIGHTARROW = 39;
			keycode = event.keyCode;
			key = String.fromCharCode(keycode).toLowerCase();
			if (keycode === KEYCODE_ESC || key.match(/x|o|c/)) {
					this.end();
			} else if (key === 'p' || keycode === KEYCODE_LEFTARROW) {
				if (this.currentImageIndex !== 0) 
					this.changeImage(this.currentImageIndex - 1);
			} else if (key === 'n' || keycode === KEYCODE_RIGHTARROW) {
				if (this.currentImageIndex !== this.album.length - 1)
					this.changeImage(this.currentImageIndex + 1);
			}
		};
		Lightbox.prototype.print = function () {
			win = window.open();
			self.focus();
			win.document.open();
			win.document.write('<html><body stlye="margin:0 auto; padding:0;">');
			win.document.write('<div align="center" style="margin: 0 auto;"><img src="' + $image.attr("src") + '" style="max-width: 100%; max-height: 90%;"/><br>' + $lightbox.find('.lb-caption').html() + '</div>');
			win.document.write('</body></html>');
			win.document.close();
			win.print();
			win.close();
   		};
		Lightbox.prototype.end = function () {
			if (isfull) this.fullScreen();
			this.disableKeyboardNav();
			$(window).off("resize", this.sizeOverlay);
			$lightbox.fadeOut(this.options.fadeDuration);
			$('#lightboxOverlay').fadeOut(this.options.fadeDuration);
			$('#lightboxOverlay').css('visibility', 'hide');
			return $('select, object, embed').css('visibility', 'visible');
		};
		return Lightbox;
	})();
	$(function () {
		var lightbox, options;
		options = new LightboxOptions;
		return lightbox = new Lightbox(options);
	});
}).call(this);
