Project Type : Spring boot 2.0.3 using mysql sql
This project present a solution for SuperMarket pricing 
Problematique 
we will imagine that we have a shopping cart with a list of item , each item contain a set of offer.
the question is how to deal with the discount when calculating the total cost of shopping Cart 

Solution of problematique : 
Creating models : -->ShoppingCart(contains a list of cartItem,...) 
                  -->CartItem (contains the item , the asked quantity , the related shoppingCart,...)
                  -->Item (contain offer , cartItem , price in bigDecimal,...)
                  -->Offer(contains a list of item , quantity condition for application and sme other information,...)
                  -->OfferType (none , unique, multiple,...)
Calcul :   -when we get the shoppingCart
                -we create a map<OfferType,List<Cartitem>>
                -the item that contain offer but we can not apply them will be consider as without offre 
            -after the creating of map, we use a desgin pattern chain of responsabilty to chain the operations of calcul of different list and we return in the end the final price
            -in our item we implement a StrategyOperation that will help us to define the strategy of discount that we will apply for each item 
 
 ps : you can change the nature of data base by changing the maven dependency and the root in application.propreties 
      i set spring.jpa.hibernate.ddl-auto = update
      # Hibernate ddl auto (create, create-drop, update): with "update" the database
      # schema will be automatically updated accordingly to java entities found in the project
 ------------------------------------------------------------------------------------------------------------------------------------

Kata01: Supermarket Pricing
This kata arose from some discussions we’ve been having at the DFW Practioners meetings. The problem domain is something seemingly simple: pricing goods at supermarkets.

Some things in supermarkets have simple prices: this can of beans costs $0.65. Other things have more complex prices. For example:

three for a dollar (so what’s the price if I buy 4, or 5?)
$1.99/pound (so what does 4 ounces cost?)
buy two, get one free (so does the third item have a price?)
This kata involves no coding. The exercise is to experiment with various models for representing money and prices that are flexible enough to deal with these (and other) pricing schemes, and at the same time are generally usable (at the checkout, for stock management, order entry, and so on). Spend time considering issues such as:

does fractional money exist?
when (if ever) does rounding take place?
how do you keep an audit trail of pricing decisions (and do you need to)?
are costs and prices the same class of thing?
if a shelf of 100 cans is priced using “buy two, get one free”, how do you value the stock?
This is an ideal shower-time kata, but be careful. Some of the problems are more subtle than they first appear. I suggest that it might take a couple of weeks worth of showers to exhaust the main alternatives.

Goal
The goal of this kata is to practice a looser style of experimental modelling. Look for as many different ways of handling the issues as possible. Consider the various tradeoffs of each. What techniques are best for exploring these models? For recording them? How can you validate a model is reasonable?






            
            
                
                  
