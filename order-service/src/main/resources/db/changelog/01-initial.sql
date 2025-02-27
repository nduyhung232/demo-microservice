-- Liquibase formatted SQL

# ChangeSet DuyHung: 1
CREATE TABLE orders (
                        order_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
                             order_item_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                             order_id INT UNSIGNED NOT NULL,
                             product_id INT UNSIGNED NOT NULL,
                             quantity INT UNSIGNED NOT NULL,
                             price DECIMAL(10,2) NOT NULL,
                             is_active BOOLEAN DEFAULT TRUE,
                             FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);