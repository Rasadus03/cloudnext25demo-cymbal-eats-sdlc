import React, { useState, useEffect } from 'react';
//import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import {Link, useParams} from 'react-router-dom';
import PropTypes from 'prop-types';


function OrderDetails({  restaurants, customer, orders}) {
  const {id} = useParams();
  const order = orders.find((r) => r.orderId === parseInt(id));
  const [orderDetails, setOrderDetails] = useState([]);
  const [data, setData] = useState([]);

    useEffect(() => {
      console.log("order === " +   JSON.stringify(orders));
      console.log ("In Order " + order+" "+(customer instanceof  Map));
      fetchOrderDetails();
    }, []);


    const fetchOrderDetails = async () => {
      if (customer instanceof  Map){
        console.log("order === " +   JSON.stringify(orders));
        console.log("order to get its details === " +  JSON.stringify(order));
        fetch("https://cymbal-eats.org/order-mgmt-api/get-order-details", {
          method: "POST",
          headers: {
            "Content-Type": "Application/JSON",
          },
          body: JSON.stringify(order),
        }).then(response => response.json())
            .then(data => {
              console.log(" data ==== "+JSON.stringify(data));
              setOrderDetails(data);
            })
            .catch((error) => {
              console.log(error);
            });
        console.log("Fetched Order details:",  JSON.stringify(orderDetails));
      }
    };


     const getRestaurantName = (restaurantId) => {
        const restaurant = restaurants.find((element) => {
          return element.restaurantId == restaurantId;
        });
       console.log("restaurant =" +restaurant);
        console.log("restaurantId =" +restaurantId+"@");
        console.log("restaurants =" +restaurants);

       // restaurants.map((field) => console.log("field ="+field.id+"@"));
        return restaurant ? restaurant.name : 'Unknown Restaurant';
    };


    return (
        <div className="cart">
          <h2>Your Order Details</h2>
          {orderDetails.orderItems !==  undefined ? (
           
                <>
                    <ul>
                      <li  className="cart-item">
                      <div>
                        Order#: {orderDetails.orderId} -----  Status: {orderDetails.status} - Total: ${parseFloat(orderDetails.totalCost)}
                      </div>
                      </li>
                      <li  className="cart-item">
                      <div>
                        OrDelivery time: {orderDetails.estimatedDeliveryTime}
                      </div>
                    </li>
                      <li  className="cart-item">
                      <div>
                       Total: ${parseFloat(orderDetails.totalCost)}
                      </div>
                      </li>
                      <li  className="cart-item">
                      {orderDetails.shippingAddress.map((shippingAddress) => (
                          <div> Delivery Address: Street: {shippingAddress.street} - Building#: .shippingAddress.buildingNumber} - Apartment#: {shippingAddress.apartmentNumber} - City: {shippingAddress.city} - ZipCode# {shippingAddress.zipcode}

                          </div>
                      ))}
                      </li>
                        {orderDetails.orderItems.map((item) => (
                            <li key={item.menuItemId} className="cart-item">
                              <img src={item.imageURL} alt={item.name}  />
                              <div>
                                {getRestaurantName(item.restaurantId)} - {item.name} - ${parseFloat(item.price).toFixed(2)}
                              </div>
                              <div>
                                Quantity:{item.quantity}
                                </div>
                            </li>
                        ))}
                    </ul>
                </>
            ):
            <p>Loading....</p>}
        </div>
    );
}

OrderDetails.propTypes = {
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
  customer: PropTypes.shape({
    name: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    uuid: PropTypes.string.isRequired,
    photoURL: PropTypes.string.isRequired,
  }).isRequired,
  orders: PropTypes.arrayOf(
      PropTypes.shape({
        estimatedDeliveryTime: PropTypes.string.isRequired,
        deliveryTime: PropTypes.string.isRequired,
        status: PropTypes.string.isRequired,
        orderId: PropTypes.number.isRequired,
        totalCost: PropTypes.number.isRequired, 
        shippingAddress: PropTypes.arrayOf(
            PropTypes.shape({
            userId:  PropTypes.string.isRequired,
            orderId: 1,
            addressId: 1,
            city:  PropTypes.string.isRequired,
            street:  PropTypes.string.isRequired,
            buildingNumber:  PropTypes.string.isRequired,
            apartmentNumber:  PropTypes.string.isRequired,
            zipCode:  PropTypes.string.isRequired
            })).isRequired, 
        orderItems: PropTypes.arrayOf(
            PropTypes.shape({
            restaurantId: PropTypes.number.isRequired,
            orderId: PropTypes.number.isRequired,
            userId: PropTypes.string.isRequired,
            menuItemId: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
            price: PropTypes.number.isRequired,
            description:PropTypes.string,
            imageURL: PropTypes.string.isRequired,
            quantity: PropTypes.number.isRequired, 
              timeAdded:PropTypes.string,
            })
        ).isRequired,
      })
  ).isRequired,
};

export default OrderDetails;