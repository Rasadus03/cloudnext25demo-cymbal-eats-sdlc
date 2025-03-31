
import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import Moment from 'moment';

function Orders({ restaurants, customer, setOrders}) {
  const [orders, setOrders2] = useState([]);
  const [order, setOrder] = useState([]);
  //get user-cart
    useEffect(() => {
      Moment.locale('en');
      fetchOrders();

    }, []);

    const fetchOrders = async () => {
      if (customer instanceof Map) {
        try {
          const response = await fetch(
              'https://cymbal-eats.org/order-mgmt-api/list-orders?user-id='
              + customer.get("email"), {mode: 'cors'}); //  Use a relative path
          console.log("Response:", response);
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          const data = await response.json();
          console.log("Fetched Orders:", data);
          setOrders(data);
          setOrders2(data);
        } catch (error) {
          console.error("Could not fetch restaurant details :", error);
          // Handle errors, e.g., display an error message to the user
        }
      }
    };


     const getRestaurantName = (restaurantId) => {
        const restaurant = restaurants.find((element) => {
          return element.id == restaurantId;
        });
       console.log("restaurant =" +restaurant);
        console.log("restaurantId =" +restaurantId+"@");
        console.log("restaurants =" +restaurants);

        restaurants.map((field) => console.log("field ="+field.id+"@"));
        return restaurant ? restaurant.name : 'Unknown Restaurant';
    };


    return (
        <div className="cart">
            <h2>Your Orders </h2>
            {orders.length === 0 ? (
                <p>Your don't have orders.</p>
            ) : (
                <>
                    <ul>
                        {orders.map((order) => (
                            <li key={order.orderId} className="cart-item">
                                <div>
                                    Order#: {order.orderId} - Delivery time: {Moment(order.estimatedDeliveryTime).format('d MMM HH:MM')} - Status: {order.status} - Total: ${parseFloat(order.totalCost)}
                                </div>
                                <div>
                                  Delivery Address: Street: {order.shippingAddress.street} - Building#: {order.shippingAddress.buildingNumber} - Apartment#: {order.shippingAddress.apartmentNumber} - City: {order.shippingAddress.city} - ZipCode# {order.shippingAddress.zipCode}
                                </div>
                              <Link  to={{
                                pathname: `/order-details/${order.orderId}`
                              }}>
                                    <button >Order Details</button>
                              </Link>
                            </li>
                        ))}
                    </ul>
                </>
            )}
        </div>
    );
}

Orders.propTypes = {
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
  setOrders: PropTypes.func.isRequired,
};

export default Orders;