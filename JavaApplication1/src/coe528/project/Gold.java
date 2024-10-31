
package coe528.project;

public class Gold implements CustomerLevel{

    @Override
    public void purchase(Customer context, double money) {
        if (money < 50) {
            return;
        }
        money += 10;
        if (context.getBalance() >= money) {
            context.withdraw(money);
        }
    }
}
