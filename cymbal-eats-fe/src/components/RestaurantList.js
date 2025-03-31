// src/components/RestaurantList.js
import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

function RestaurantList({ restaurants }) {
  return (
    <div className="restaurant-list">
      <h2>Restaurants</h2>
      {restaurants.length === 0 ? (
          <p>No restaurants found.</p>
      ) : (
        <div className='restaurant-cards-container'>
        {restaurants.map((restaurant) => (
          <div key={restaurant.restaurantId} className="restaurant-card">
            <img src={restaurant.imageURL} alt={restaurant.name} />
            <h3>
              <Link to={`/restaurants/${restaurant.restaurantId}`}>{restaurant.name}</Link>
            </h3>
            <p>Cuisine: {restaurant.cuisine}</p>
            <p>{restaurant.description}</p>
             <Link to={`/restaurants/${restaurant.restaurantId}`} className="view-menu-button">View Menu</Link>
          </div>
        ))}
        </div>
      )}
    </div>
  );
}

// Add PropTypes for type checking
RestaurantList.propTypes = {
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
  ).isRequired
};

export default RestaurantList;