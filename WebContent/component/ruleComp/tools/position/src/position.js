define(function(require, exports) {

			// Position
			// --------
			// ��λ�����������һ�� DOM �ڵ���Զ���һ�� DOM �ڵ���ж�λ������
			// �����׸ģ������ѵ�

			var Position = exports, VIEWPORT = {
				_id : 'VIEWPORT',
				nodeType : 1
			}, $ = require('$'), isPinFixed = false, isIE6 = $.browser.msie
					&& $.browser.version == 6.0;

			// ��Ŀ��Ԫ������ڻ�׼Ԫ�ؽ��ж�λ
			// ���� Position �Ļ������������������������ֱ�������Ŀ��Ԫ�غͻ�׼Ԫ�صĶ�λ��
			Position.pin = function(pinObject, baseObject) {

				// ����������ת���ɱ�׼��λ���� { element: a, x: 0, y: 0 }
				pinObject = normalize(pinObject);
				baseObject = normalize(baseObject);

				// �趨Ŀ��Ԫ�ص� position Ϊ���Զ�λ
				// ��Ԫ�صĳ�ʼ position ��Ϊ absolute����Ӱ��Ԫ�ص� display����ߵ�����
				var pinElement = $(pinObject.element);

				if (pinElement.css('position') !== 'fixed' || isIE6) {
					pinElement.css('position', 'absolute');
					isPinFixed = false;
				} else {
					// ��λ fixed Ԫ�صı�־λ�����������⴦��
					isPinFixed = true;
				}

				// ��λ�����Թ�һ��Ϊ��ֵ
				// ע���������������� `css('position', 'absolute')` ֮��
				// �����ȡ�Ŀ���п��ܲ���
				posConverter(pinObject);
				posConverter(baseObject);

				var parentOffset = getParentOffset(pinElement);
				var baseOffset = baseObject.offset();

				// ����Ŀ��Ԫ�ص�λ��
				var top = baseOffset.top + baseObject.y - pinObject.y
						- parentOffset.top;

				var left = baseOffset.left + baseObject.x - pinObject.x
						- parentOffset.left;

				// ��λĿ��Ԫ��
				pinElement.css({
							left : left,
							top : top
						});
			};

			// ��Ŀ��Ԫ������ڻ�׼Ԫ�ؽ��о��ж�λ
			// ���������������ֱ�ΪĿ��Ԫ�غͶ�λ�Ļ�׼Ԫ�أ����� DOM �ڵ�����
			Position.center = function(pinElement, baseElement) {
				Position.pin({
							element : pinElement,
							x : '50%',
							y : '50%'
						}, {
							element : baseElement,
							x : '50%',
							y : '50%'
						});
			};

			// ���ǵ�ǰ���������α DOM �ڵ�
			// ��Ҫ����ڵ�ǰ��������λʱ���ɴ���˶�����Ϊ element ����
			Position.VIEWPORT = VIEWPORT;

			// Helpers
			// -------

			// ��������װ�ɱ�׼�Ķ�λ�������� { element: a, x: 0, y: 0 }
			function normalize(posObject) {
				posObject = toElement(posObject) || {};

				if (posObject.nodeType) {
					posObject = {
						element : posObject
					};
				}

				var element = toElement(posObject.element) || VIEWPORT;
				if (element.nodeType !== 1) {
					throw new Error('posObject.element is invalid.');
				}

				var result = {
					element : element,
					x : posObject.x || 0,
					y : posObject.y || 0
				};

				// config ����ȿ�¡���滻�� Position.VIEWPORT, ����ֱ�ӱȽ�Ϊ false
				var isVIEWPORT = (element === VIEWPORT || element._id === 'VIEWPORT');

				// ��һ�� offset
				result.offset = function() {
					// ����λ fixed Ԫ�أ���Ԫ�ص� offset û������
					if (isPinFixed) {
						return {
							left : 0,
							top : 0
						};
					} else if (isVIEWPORT) {
						return {
							left : $(document).scrollLeft(),
							top : $(document).scrollTop()
						};
					} else {
						return $(element).offset();
					}
				};

				// ��һ�� size, �� padding �� border
				result.size = function() {
					var el = isVIEWPORT ? $(window) : $(element);
					return {
						width : el.outerWidth(),
						height : el.outerHeight()
					};
				};

				return result;
			}

			// �� x, y ��������Ϊ left|center|right|%|px ʱ�Ĵ���ȫ������Ϊ������
			function posConverter(pinObject) {
				pinObject.x = xyConverter(pinObject.x, pinObject, 'width');
				pinObject.y = xyConverter(pinObject.y, pinObject, 'height');
			}

			// ���� x, y ֵ����ת��Ϊ����
			function xyConverter(x, pinObject, type) {
				// ��ת���ַ�����˵���ô���
				x = x + '';

				// ���� px
				x = x.replace(/px/gi, '');

				// ���� alias
				if (/\D/.test(x)) {
					x = x.replace(/(?:top|left)/gi, '0%').replace(/center/gi,
							'50%').replace(/(?:bottom|right)/gi, '100%');
				}

				// ���ٷֱ�תΪ����ֵ
				if (x.indexOf('%') !== -1) {
					// ֧��С��
					x = x.replace(/(\d+(?:\.\d+)?)%/gi, function(m, d) {
								return pinObject.size()[type] * (d / 100.0);
							});
				}

				// �������� 100%+20px �����
				if (/[+\-*\/]/.test(x)) {
					try {
						// eval ��Ӱ��ѹ��
						// new Function ����Ч�ʸ��� for ѭ�����ַ����ķ���
						// ���գ�http://jsperf.com/eval-newfunction-for
						x = (new Function('return ' + x))();
					} catch (e) {
						throw new Error('Invalid position value: ' + x);
					}
				}

				// ת��Ϊ����
				return numberize(x);
			}

			// ��ȡ offsetParent ��λ��
			function getParentOffset(element) {
				var parent = element.offsetParent();

				if (parent[0] === document.documentElement) {
					parent = $(document.body);
				}

				// ���� ie6 �� absolute ��λ��׼�� bug
				if (isIE6) {
					parent.css('zoom', 1);
				}

				// ��ȡ offsetParent �� offset
				// ע1��document.body ��Ĭ�ϴ� 8 ���ص�ƫ��
				//
				// ע2��IE7 �£�body �ӽڵ�� offsetParent Ϊ html Ԫ�أ��� offset Ϊ
				// { top: 2, left: 2 }���ᵼ�¶�λ�� 2 ���أ��������ｫ parent
				// תΪ document.body
				//
				// �����������ֱ�Ӹ�Ϊ 0
				var offset = (parent[0] === document.body) ? {
					left : 0,
					top : 0
				} : parent.offset();

				// ���ݻ�׼Ԫ�� offsetParent �� border ��ȣ������� offsetParent �Ļ�׼λ��
				offset.top += numberize(parent.css('border-top-width'));
				offset.left += numberize(parent.css('border-left-width'));

				return offset;
			}

			function numberize(s) {
				return parseFloat(s, 10) || 0;
			}

			function toElement(element) {
				return $(element)[0];
			}

		});
