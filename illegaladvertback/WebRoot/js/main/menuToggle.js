 (function($){
        $.fn.extend({'menuToggle':
            function(options){
                //self变量，用于函数内部调用插件参数
                var self = this;
                //默认参数
                this._default = {
                    'ctrlBtn':null,            //关闭&展开按钮id
                    'speed':400,            //展开速度
                    'width':400,            //展开菜单宽度
                    'height':400,            //展开菜单高度
                    'openText':'展开>>',    //展开前文本
                    'closeText':'<<关闭',    //展开后文本
                    'contextDiv':null,      //菜单展开，相应的内容div将缩小，反之，则扩大
                    'type':'width'            //width表示按宽度伸展，height表示按高度伸展
                };
                //插件初始化函数
                this.init = function(options){
                    //配置参数格式有误则提示并返回
                    if(typeof options != 'object') {
                        self.error('Options is not object Error!');
                        return false;
                    }
                    if(typeof options.ctrlBtn == 'undefined') {
                        self.error('Options ctrlBtn should not be empty!');
                        return false;
                    }
                    //存储自定义参数
                    self._default.ctrlBtn = options.ctrlBtn;
                    if(typeof options.type != 'undefined')         self._default.type = options.type;
                    if(typeof options.width != 'undefined')     self._default.width = options.width;
                    if(typeof options.height != 'undefined')     self._default.height = options.height;
                    if(typeof options.speed != 'undefined')     self._default.speed = options.speed;
                    if(typeof options.openText != 'undefined')     self._default.openText = options.openText;
                    if(typeof options.closeText != 'undefined') self._default.closeText = options.closeText;
                    if(typeof options.contextDiv != 'undefined') {
                    	self._default.contextDivFlag=true;
                    	self._default.contextDiv = options.contextDiv;
                    }
                    if(self._default.type == 'width') {
                        self._default.expandOpen     = {width: self._default.width};
                        self._default.expandClose     = {width: 0};
                    }else {
                        self._default.expandOpen     = {height: self._default.height};
                        self._default.expandClose     = {height: 0};
                    }
                };
                this.run = function(){
                    $("#"+self._default.ctrlBtn).click(function () {
                    	 
                        if($(this).hasClass('closed')){        //有closed类，表示已关闭，现在展开
                        	 $(this).removeClass('closed');
                        	 $(self).show().animate(self._default.expandOpen, self._default.speed);
                        	 if(self._default.contextDivFlag){
                        		 self._default.expandOtherOpen={width: $("#"+self._default.contextDiv).outerWidth()-self._default.width,marginLeft:0};
                            	$("#"+self._default.contextDiv).animate(self._default.expandOtherOpen, self._default.speed);
                            }
                        }else {
                            $(this).addClass('closed');
                            $(self).animate(self._default.expandClose, self._default.speed,function(){
                                $(this).hide();
                            });
                            self._default.expandOtherOpen={width: ($("#"+self._default.contextDiv).outerWidth()+self._default.width),marginLeft:-self._default.width};
                            if(self._default.contextDivFlag){
                            	$("#"+self._default.contextDiv).animate(self._default.expandOtherOpen, self._default.speed);
                            }
                        }
                    });
                };
                this.error = function(msg){
                    //没有错误提示DIV则自动添加
                    if(!$("#menuToggleErrorTips").size()){
                        $("<div id='menuToggleErrorTips'><h2>Error</h2><div class='tips'></div></div>").appendTo($("body")).hide();
                        $("#menuToggleErrorTips").css({
                            position:'absolute',
                            left: $("body").width()/3,
                            top: 100,
                            width:400,
                            height:200,
                            'z-index': 9999999,
                            'border': '1px solid #000',
                            'background-color': '#ABC',
                            'color': '#CC0000',
                            'text-align': 'center'
                        });
                    }
                    //显示错误提示信息
                    $("#menuToggleErrorTips").show().children('.tips').html(msg);
                }
                //Init
                this.init(options);
                this.run();
            }
        });
    })(jQuery);