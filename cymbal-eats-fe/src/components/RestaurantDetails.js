// src/components/RestaurantDetails.js
import React, {useEffect, useState} from 'react';
import { useParams } from 'react-router-dom';
import PropTypes from 'prop-types';

function RestaurantDetails({ restaurants, addToCart }) {
  const {id} = useParams();
  const restaurant = restaurants.find((r) => r.restaurantId === parseInt(id));
  const [restaurantMenu, setRestaurantMenu] = useState([]);
  const [isBusy, setBusy] = useState(true)

  //get Restaurant's Menu
  useEffect(() => {

    fetchRestaurantdetails();

  }, []);

  const fetchRestaurantdetails = async () => {
    try {
      const response = await fetch(
            'https://cymbal-eats.org/restaurant-details-api/restaurant-menu?id='
          + id, {mode: 'cors'}); //  Use a relative path
      console.log("Response:", response);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      console.log("Fetched restaurant details:", data);
      setRestaurantMenu(data);
    } catch (error) {
      console.error("Could not fetch restaurant details :", error);
      // Handle errors, e.g., display an error message to the user
    }
  };

  if (!restaurant) {
    return <div>Restaurant not found.</div>;
  }

  return (

      <div className="restaurant-details">
        <h2>{restaurant.name}</h2>
        <img src={restaurant.imageURL} alt={restaurant.name}/>
        <p>Cuisine: {restaurant.cuisine}</p>
        <p>{restaurant.description}</p>
        <h3>Menu</h3>
        <ul>
          {restaurantMenu.map((item) => (
              <li key={item.menuItemId} className="menu-item">
                <img src={item.imageURL} alt={item.name}  />
                <span>{item.name} - ${item.price.toFixed(2)}</span>
                <span>{item.description}</span>
                <button onClick={() => addToCart(restaurant.restaurantId, item)}>Add to
                  Cart
                </button>
              </li>
          ))}
        </ul>
      </div>

  );
}
RestaurantDetails.propTypes = {
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
  addToCart: PropTypes.func.isRequired,
};

export default RestaurantDetails;