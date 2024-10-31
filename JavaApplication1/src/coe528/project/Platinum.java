
package coe528.project;

public class Platinum implements CustomerLevel{

    @Override
    public void purchase(Customer context, double money) {
        if (money < 50) {
            return;
        }
        if (context.getBalance() >= money) {
            context.withdraw(money);
        }
    }
}
