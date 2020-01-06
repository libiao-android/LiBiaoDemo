
1、Android中动态设置drawable中的shape的solid属性颜色

GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.register_login_j);
int BankColor = Color.parseColor("#000000");
drawable.setColor(BankColor);
view.setBackgroundDrawable(drawable);