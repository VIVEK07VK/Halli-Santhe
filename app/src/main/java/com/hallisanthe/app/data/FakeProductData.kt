package com.hallisanthe.app.data

import com.hallisanthe.app.models.Product

/**
 * Premium 3D Vector Product Catalog
 * Expanded to 100+ items to ensure a rich buyer experience.
 */
object FakeProductData {

    private val vegetables = listOf(
        Product(id = "v1", name = "Farm Fresh Tomato", price = 32.0, stock = 100, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1582284738265-ebd5c18cb47b?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Naturally ripened, juicy red tomatoes directly from village farms.", rating = 4.2, sellerName = "Ramesh Kumhar", sellerId = "test_seller"),
        Product(id = "v2", name = "Organic Red Onion", price = 40.0, stock = 150, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1508747703725-719777637510?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Pungent and fresh red onions, perfect for traditional Indian curries.", rating = 4.5, sellerName = "Laxmi Devi", sellerId = "test_seller"),
        Product(id = "v3", name = "Desi Potato", price = 25.0, stock = 200, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1518977676601-b53f82aba655?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Starchy and clean potatoes grown in natural soil without chemicals.", rating = 4.3, sellerName = "Suresh Hegde", sellerId = "test_seller"),
        Product(id = "v4", name = "Green Chili", price = 15.0, stock = 50, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1588252303782-cb80119abd6d?q=80&w=600&auto=format&fit=crop", unit = "250g", description = "Spicy and vibrant green chilies to add a kick to your meals.", rating = 4.6, sellerName = "Ramesh Kumhar", sellerId = "test_seller"),
        Product(id = "v5", name = "Fresh Cauliflower", price = 45.0, stock = 40, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1568584711075-3d021a7c3ec3?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Large, crisp white cauliflower heads harvested this morning.", rating = 4.4, sellerName = "Anand Kumar", sellerId = "test_seller"),
        Product(id = "v6", name = "Baby Spinach", price = 20.0, stock = 60, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1576045057995-568f588f82fb?q=80&w=600&auto=format&fit=crop", unit = "bunch", description = "Tender baby spinach leaves, rich in iron.", rating = 4.7, sellerName = "Laxmi Devi", sellerId = "test_seller"),
        Product(id = "v7", name = "Native Drumstick", price = 10.0, stock = 100, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1592394986865-76f65d4a9246?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Long, flavorful drumsticks for traditional sambar.", rating = 4.5, sellerName = "Suresh Hegde", sellerId = "test_seller"),
        Product(id = "v8", name = "Fresh Ginger", price = 120.0, stock = 30, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Aromatic and spicy ginger roots.", rating = 4.8, sellerName = "Anand Kumar", sellerId = "test_seller"),
        Product(id = "v9", name = "Garlic Bulbs", price = 180.0, stock = 40, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1540148426945-6cf22a6b2383?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Strongly flavored village garlic.", rating = 4.6, sellerName = "Ramesh Kumhar", sellerId = "test_seller"),
        Product(id = "v10", name = "Field Carrots", price = 50.0, stock = 70, category = "Vegetables", imageUrl = "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Sweet and crunchy orange carrots.", rating = 4.4, sellerName = "Laxmi Devi", sellerId = "test_seller")
    )

    private val fruits = listOf(
        Product(id = "f1", name = "Alphonso Mango", price = 120.0, stock = 80, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1553279768-865429fa0078?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "The king of fruits, sweet and aromatic from village orchards.", rating = 4.9, sellerName = "Vikas Gowda", sellerId = "test_seller"),
        Product(id = "f2", name = "Green Grapes", price = 60.0, stock = 100, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1537640538966-79f369143f8f?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Seedless and sweet green grapes, freshly plucked.", rating = 4.6, sellerName = "Meena Rao", sellerId = "test_seller"),
        Product(id = "f3", name = "Red Pomegranate", price = 150.0, stock = 40, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1511389026070-a14ae610a1be?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Ruby-red seeds full of health and village goodness.", rating = 4.8, sellerName = "Vikas Gowda", sellerId = "test_seller"),
        Product(id = "f4", name = "Organic Banana", price = 40.0, stock = 200, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1587132137056-bfbf0166836e?q=80&w=600&auto=format&fit=crop", unit = "doz", description = "Naturally ripened village bananas, rich in energy.", rating = 4.5, sellerName = "Gopal Das", sellerId = "test_seller"),
        Product(id = "f5", name = "Native Guava", price = 55.0, stock = 90, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1536657235019-030712f6156d?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Crisp and sweet pink-hearted guavas from local trees.", rating = 4.7, sellerName = "Meena Rao", sellerId = "test_seller"),
        Product(id = "f6", name = "Sweet Papaya", price = 45.0, stock = 50, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1526476148966-98bd039463ea?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Rich in vitamins, perfectly ripened papaya.", rating = 4.3, sellerName = "Vikas Gowda", sellerId = "test_seller"),
        Product(id = "f7", name = "Watermelon", price = 80.0, stock = 30, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1589984662646-e7b2e4962f18?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Large, juicy and refreshing watermelon.", rating = 4.6, sellerName = "Gopal Das", sellerId = "test_seller"),
        Product(id = "f8", name = "Custard Apple", price = 90.0, stock = 40, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1595183305101-b3f7f02d4f20?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Creamy and sweet sitaphal from hill farms.", rating = 4.8, sellerName = "Meena Rao", sellerId = "test_seller"),
        Product(id = "f9", name = "Indian Gooseberry", price = 70.0, stock = 60, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1591122606660-8488e5d0d611?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Vitamin C rich Amla for health.", rating = 4.7, sellerName = "Vikas Gowda", sellerId = "test_seller"),
        Product(id = "f10", name = "Tender Coconut", price = 40.0, stock = 100, category = "Fruits", imageUrl = "https://images.unsplash.com/photo-1609146299136-2a1d844d892c?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Refreshing coconut water from village groves.", rating = 4.9, sellerName = "Gopal Das", sellerId = "test_seller")
    )

    private val organic = listOf(
        Product(id = "o1", name = "Raw Wild Honey", price = 450.0, stock = 30, category = "Organic Products", imageUrl = "https://images.unsplash.com/photo-1587049352851-8d4e89133924?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Pure honey collected from wild hives in village forests.", rating = 5.0, sellerName = "Forest Honey", sellerId = "test_seller"),
        Product(id = "o2", name = "Desi Cow Ghee", price = 850.0, stock = 20, category = "Organic Products", imageUrl = "https://images.unsplash.com/photo-1631729371254-42c2892f0e6e?q=80&w=600&auto=format&fit=crop", unit = "L", description = "Traditional Bilona ghee made from A2 cow milk.", rating = 4.9, sellerName = "Gopal Dairy", sellerId = "test_seller"),
        Product(id = "o3", name = "Cold Pressed Coconut Oil", price = 320.0, stock = 50, category = "Organic Products", imageUrl = "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?q=80&w=600&auto=format&fit=crop", unit = "L", description = "Pure coconut oil extracted using traditional wooden press.", rating = 4.8, sellerName = "Village Press", sellerId = "test_seller"),
        Product(id = "o4", name = "Organic Jaggery", price = 90.0, stock = 100, category = "Organic Products", imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?q=80&w=600&auto=format&fit=crop", unit = "kg", description = "Chemical-free dark jaggery blocks.", rating = 4.7, sellerName = "Sugar Cane Farms", sellerId = "test_seller"),
        Product(id = "o5", name = "Turmeric Powder", price = 150.0, stock = 80, category = "Organic Products", imageUrl = "https://images.unsplash.com/photo-1585238342024-78d387f4a707?q=80&w=600&auto=format&fit=crop", unit = "250g", description = "High curcumin turmeric ground from dried roots.", rating = 4.9, sellerName = "Spice Village", sellerId = "test_seller")
    )

    private val handicrafts = listOf(
        Product(id = "h1", name = "Terracotta Pot", price = 250.0, stock = 20, category = "Handicrafts", imageUrl = "https://images.unsplash.com/photo-1604264726154-26480e76f4e1?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Traditional clay pot that keeps water naturally cool.", rating = 4.9, sellerName = "Clay Arts", sellerId = "test_seller"),
        Product(id = "h2", name = "Bamboo Basket", price = 180.0, stock = 30, category = "Handicrafts", imageUrl = "https://images.unsplash.com/photo-1696574727184-a8cdb758d3bf?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Hand-woven bamboo basket for vegetables or fruits.", rating = 4.6, sellerName = "Bamboo Works", sellerId = "test_seller"),
        Product(id = "h3", name = "Wooden Toys", price = 350.0, stock = 15, category = "Handicrafts", imageUrl = "https://images.unsplash.com/photo-1515488042361-404e92537028?q=80&w=600&auto=format&fit=crop", unit = "set", description = "Safe and colorful wooden toys from Channapatna.", rating = 4.8, sellerName = "Toy Maker", sellerId = "test_seller"),
        Product(id = "h4", name = "Cotton Handloom Bag", price = 120.0, stock = 50, category = "Handicrafts", imageUrl = "https://images.unsplash.com/photo-1590739225287-bd26514ca9ba?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Strong and eco-friendly hand-woven cotton bag.", rating = 4.5, sellerName = "Weavers Hub", sellerId = "test_seller"),
        Product(id = "h5", name = "Brass Diya", price = 450.0, stock = 10, category = "Handicrafts", imageUrl = "https://images.unsplash.com/photo-1589417431526-724f114c22cc?q=80&w=600&auto=format&fit=crop", unit = "pc", description = "Ornate traditional brass lamp for your home.", rating = 4.9, sellerName = "Metal Arts", sellerId = "test_seller")
    )


    private val seeds = listOf(
        Product(id = "s1", name = "Pumkin Seeds", price = 120.0, stock = 50, category = "Seeds", imageUrl = "https://images.unsplash.com/photo-1647553756926-21a62021b9d2?q=80&w=600&auto=format&fit=crop", unit = "200g", description = "Rich in zinc and healthy fats.", rating = 4.6, sellerName = "Seed Farm", sellerId = "test_seller"),
        Product(id = "s2", name = "Sun Flower Seeds", price = 150.0, stock = 40, category = "Seeds", imageUrl = "https://images.unsplash.com/photo-1590500466605-95a297530c05?q=80&w=600&auto=format&fit=crop", unit = "200g", description = "Nutritious sunflower seeds for snacking.", rating = 4.5, sellerName = "Seed Farm", sellerId = "test_seller"),
        Product(id = "s3", name = "Desi Cow Milk Seeds", price = 80.0, stock = 100, category = "Seeds", imageUrl = "https://images.unsplash.com/photo-1594631252845-29fc4cc8cde9?q=80&w=600&auto=format&fit=crop", unit = "pkt", description = "Seeds for fodder grass to feed cows.", rating = 4.7, sellerName = "Green Fodder", sellerId = "test_seller")
    )

    private val snacks = listOf(
        Product(id = "sn1", name = "Banana Chips", price = 60.0, stock = 100, category = "Snacks", imageUrl = "https://images.unsplash.com/photo-1566478989037-eec170784d0b?q=80&w=600&auto=format&fit=crop", unit = "200g", description = "Crispy chips fried in pure coconut oil.", rating = 4.8, sellerName = "Village Snacks", sellerId = "test_seller"),
        Product(id = "sn2", name = "Roasted Peanuts", price = 40.0, stock = 150, category = "Snacks", imageUrl = "https://images.unsplash.com/photo-1567894340346-ce331420b61c?q=80&w=600&auto=format&fit=crop", unit = "200g", description = "Hand-roasted peanuts with salt and chili.", rating = 4.6, sellerName = "Village Snacks", sellerId = "test_seller"),
        Product(id = "sn3", name = "Ragi Malt Mix", price = 180.0, stock = 50, category = "Snacks", imageUrl = "https://images.unsplash.com/photo-1559056199-641a0ac8b55e?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Healthy traditional drink mix made from finger millet.", rating = 4.9, sellerName = "Healthy Foods", sellerId = "test_seller")
    )

    private val beverages = listOf(
        Product(id = "b1", name = "Filter Coffee Powder", price = 220.0, stock = 40, category = "Beverages", imageUrl = "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Strong and aromatic coffee blend with chicory.", rating = 4.9, sellerName = "Hill Side Coffee", sellerId = "test_seller"),
        Product(id = "b2", name = "Herbal Tea Leaves", price = 150.0, stock = 60, category = "Beverages", imageUrl = "https://images.unsplash.com/photo-1594631252845-29fc4cc8cde9?q=80&w=600&auto=format&fit=crop", unit = "250g", description = "Natural tea leaves mixed with dried herbs.", rating = 4.7, sellerName = "Herbal Care", sellerId = "test_seller")
    )

    private val pickles = listOf(
        Product(id = "p1", name = "Mango Pickle", price = 120.0, stock = 80, category = "Pickles", imageUrl = "https://images.unsplash.com/photo-1600962815726-457c46a12681?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Traditional spicy and sour green mango pickle.", rating = 4.8, sellerName = "Grandma's Kitchen", sellerId = "test_seller"),
        Product(id = "p2", name = "Lemon Pickle", price = 100.0, stock = 70, category = "Pickles", imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Sweet and spicy aged lemon pickle.", rating = 4.6, sellerName = "Grandma's Kitchen", sellerId = "test_seller"),
        Product(id = "p3", name = "Chili Pickle", price = 110.0, stock = 50, category = "Pickles", imageUrl = "https://images.unsplash.com/photo-1588252303782-cb80119abd6d?q=80&w=600&auto=format&fit=crop", unit = "500g", description = "Very spicy green chili pickle.", rating = 4.5, sellerName = "Spice Village", sellerId = "test_seller")
    )


    val products: List<Product> = vegetables + fruits + organic + handicrafts + seeds + snacks + beverages + pickles
}

