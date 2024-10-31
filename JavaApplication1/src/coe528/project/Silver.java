
package coe528.project;

public class Silver implements CustomerLevel {
    
    @Override
    public void purchase(Customer context, double money) {
        if (money < 50) {
            return;
        }
        money += 20;
        if (context.getBalance() >= money) {
            context.withdraw(money);
        }
    }
}
