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
      # schema will be automatically updated accordingly to java entities found in








            
            
                
                  
