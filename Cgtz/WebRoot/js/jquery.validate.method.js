 jQuery.validator.addMethod("regex",  //addMethod第1个参数:方法名称
        function(value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
            var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
            return exp.test(value);                    //测试是否匹配
        },
        "格式错误");    //addMethod第3个参数:默认错误信息
        $(function() {
            $("#signupForm").validate(

            {
            rules: {
                txtPassword1: "required",  //密码1必填

                txtPassword2: {    //密码2的描述多于1项使用对象类型
                    required: true,  //必填，这里可以是一个匿名方法
                    equalTo: "#txtPassword1",  //必须与密码1相等
                    rangelength: [5, 10],    //长度5-10之间
                    regex: "^\\w+$"          //使用自定义的验证规则,在上例中新增的部分
                },

                txtEmail: "email"   //电子邮箱必须合法
            },
            messages: {
                txtPassword1: "您必须填写",

                txtPassword2: {
                    required: "您必须填写",
                    equalTo: "密码不一致",
                    rangelength: "长度必须介于{0} 和 {1} 之间的字符串",
                    regex: "密码只能是数字、字母与下划线"
                }
            },
            debug: false,  //如果修改为true则表单不会提交
            submitHandler: function() {
                alert("开始提交了");
            }
        });
    });
