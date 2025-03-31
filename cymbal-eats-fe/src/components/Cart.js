// src/components/Cart.js
import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import {uuid} from 'short-uuid';


function Cart({  restaurants, removeFromCart, updateQuantity, clearCart, setCartItems, customer }) {
  const [shoppingCartItems, setCart] = useState([]);
  //get user-cart
    useEffect(() => {
      console.log("Rania inside!");
      fetchShoppingCart();

    }, [customer]);

    const fetchShoppingCart = async () => {
      console.log("@ cart customer instanceof Map == "+(customer instanceof Map));
      if (customer instanceof Map){
        const response = await fetch(
            'https://cymbal-eats.org/shopping-cart-api/view-shopping-cart?user-id='
            + customer.get("email"), {mode: 'cors'}); //  Use a relative path
        console.log("Response:", response);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        console.log("Fetched cart:", data);
       // const transformed = data.map(({ restaurantId, restaurantName , itemId, itemName, itemDescription, itemPrice, itemImageUrl, timeAdded, userId, quantity }) => ({ restaurantId: restaurantId, restaurantName: restaurantName , id: itemId, name: itemName, descripton: itemDescription, price: itemPrice, image: itemImageUrl, timeAdded: timeAdded, userId: userId, quantity:quantity}));
        setCart(data);
        setCartItems (data);
        console.log("Loaded Cart =" +data);
      }
    };

    const getTotalPrice = () => {
        return shoppingCartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    };

     const getRestaurantName = (restaurantId) => {
        const restaurant = restaurants.find((element) => {
          return element.restaurantId == restaurantId;
        });
       console.log("restaurant =" +restaurant);
        console.log("restaurantId =" +restaurantId+"@");
        console.log("restaurants =" +restaurants);

        restaurants.map((field) => console.log("field ="+field.restaurantId+"@"));
        return restaurant ? restaurant.name : 'Unknown Restaurant';
    };


    return (
        <div className="cart">
            <h2>Your Cart</h2>
            {shoppingCartItems.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                <>
                    <ul>
                        {shoppingCartItems.map((item) => (
                            <li key={item.menuItemId} className="cart-item">
                              <img src={item.imageURL} alt={item.name}  />
                                <div>
                                    {getRestaurantName(item.restaurantId)} - {item.name} - ${parseFloat(item.price).toFixed(2)}
                                </div>
                                <div>
                                    Quantity:
                                    <input
                                        type="number"
                                        min="1"  // Ensure quantity is at least 1
                                        value={item.quantity}
                                        onChange={(e) => updateQuantity(item.restaurantId, item, parseInt(e.target.value),setCart, shoppingCartItems)}
                                    />
                                    <button onClick={() => removeFromCart(item.restaurantId, item, setCart, shoppingCartItems)}>Remove</button>
                                </div>
                            </li>
                        ))}
                    </ul>
                    <p>Total: ${getTotalPrice().toFixed(2)}</p>
                    <button onClick={() => clearCart(customer.get("email"), setCart)}>Clear Cart</button>
                     <Link to="/checkout">
                        <button>Proceed to Checkout</button>
                    </Link>
                </>
            )}
        </div>
    );
}

Cart.propTypes = {
    customer: PropTypes.shape({
      name: PropTypes.string.isRequired,
      email: PropTypes.string.isRequired,
      uuid: PropTypes.string.isRequired,
      photoURL: PropTypes.string.isRequired,
    }).isRequired,
    restaurants: PropTypes.arrayOf(
    PropTypes.shape({
      restaurantId: PropTypes.number.isRequired,
      name: PropTypes.string.isRequired,
      cuisines: PropTypes.arrayOf(PropTypes.shape({
        couisineId: PropTypes.number.isRequired,
        couisineName: PropTypes.string.isRequired,
        restaurantId: PropTypes.number.isRequired
      })).isRequired,
      description: PropTypes.string.isRequired,
      imageURL: PropTypes.string.isRequired,
      menuItems: PropTypes.arrayOf(
        PropTypes.shape({
          menuItemId: PropTypes.number.isRequired,
          name: PropTypes.string.isRequired,
          price: PropTypes.number.isRequired,
          description:PropTypes.string,
        })
      ).isRequired,
    })
  ).isRequired,
    removeFromCart: PropTypes.func.isRequired,
    updateQuantity: PropTypes.func.isRequired,
     clearCart: PropTypes.func.isRequired,
  setCartItems: PropTypes.func.isRequired,
};

export default Cart;