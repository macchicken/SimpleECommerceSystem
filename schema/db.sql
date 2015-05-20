CREATE TABLE simple_order (
  id varchar(50) NOT NULL,
  address1 varchar(150) DEFAULT NULL,
  address2 varchar(150) DEFAULT NULL,
  city varchar(100) DEFAULT NULL,
  shippingCost double DEFAULT NULL,
  totalCost double DEFAULT NULL,
  state varchar(50) DEFAULT NULL,
  user varchar(100) DEFAULT NULL,
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE simple_order_item (
  id varchar(50) NOT NULL,
  order_id varchar(50) NOT NULL,
  quantity int(11) DEFAULT NULL,
  product blob,
  PRIMARY KEY (id,order_id),
  KEY idx_simple_order_item_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;