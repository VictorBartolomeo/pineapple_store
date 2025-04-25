-- Inserer des utilisateurs (user)
INSERT INTO user (email, password)
VALUES ('a@a.com', '$2a$10$1vDZ9Kv47brekbaDy1rXNeOvtobg38CoQBtCDtfwPYJNAOoVkDBdK'),
       ('b@b.com', '$2a$10$1vDZ9Kv47brekbaDy1rXNeOvtobg38CoQBtCDtfwPYJNAOoVkDBdK'),
       ('c@c.com', '$2a$10$1vDZ9Kv47brekbaDy1rXNeOvtobg38CoQBtCDtfwPYJNAOoVkDBdK'),
       ('d@d.com', '$2a$10$1vDZ9Kv47brekbaDy1rXNeOvtobg38CoQBtCDtfwPYJNAOoVkDBdK');

-- Inserer des vendeurs

INSERT INTO seller(id, salary, chief)
VALUES (1, 2000, false),
       (3, 3000, true),
       (4, 3000, false);

-- Inserer des clients
INSERT INTO client(id, client_number)
VALUES (2, 'KOBE24HUIT');

-- Insérer les états (states)
INSERT INTO state (name)
VALUES ('New (Freshly Picked)'),
       ('Like New (Lightly Squeezed)'),
       ('Used (Ripe & Ready)'),
       ('Refurbished (Re-Pitted)'),
       ('Damaged (Slightly Bruised)');

-- Insérer les étiquettes (labels)
INSERT INTO label (name, color)
VALUES ('Pineapple Deals', 'rgba(255,0,0,0.30)'),
       ('Tropical Holidays', 'rgba(0,128,0,0.30)'),
       ('Winter Sun Specials', 'rgba(0,0,255,0.30)'),
       ('Pineapple Friday', 'rgba(255,0,0,0.30)'),
       ('Golden Harvest', 'rgba(255,165,0,0.30)'),
       ('Back to the Grove', 'rgba(128,0,128,0.30)'),
       ('Pineapple Essentials', 'rgba(255,192,203,0.30)'),
       ('Limited Edition', 'rgba(128,128,128,0.30)');

-- Insérer les produits (products)
INSERT INTO product (name, code, description, price, state_id, creator_id)
VALUES ('PineApple Watch SE', 'PAWSE00001', 'Features at a great price.', 299.99, 1, 1),
       ('PinePhone 15', 'PAP1500002', 'Big features. Big value.', 999.99, 2, 1),
       ('PinePad 10.2-inch', 'PAP1000003', 'Versatility at your fingertips.', 449.99, 3, 1),
       ('PinePad Pro 12.9-inch', 'PAPPM12004', 'The ultimate PinePad experience.', 1799.99, 5, 1),
       ('PinePods', 'PAPOD00005', 'Wireless earbuds. Simply magical.', 159.99, 4, 1),
       ('PineBook', 'PABOK00006', 'Our most popular laptop. Now even more powerful.', 899.99, 2, 1),
       ('PineVision', 'PAVIS00007', 'Transform the way you see and interact with content.', 2999.99, 1, 1),
       ('PineApple Watch Ultra 2', 'PAWU200008', 'Further. Beyond.', 899.99, 1, 1),
       ('PinePhone 15 Pro Max', 'PAP15PM009', 'The best PinePhone ever.', 1299.99, 5, 3),
       ('PinePad mini', 'PAPMI00010', 'Mega power. Mini size.', 649.99, 1, 3),
       ('PinePods Max', 'PAPMAX0011', 'Effortless Pineapple listening.', 549.99, 3, 3),
       ('PineBook Pro 14', 'PABP140012', 'Supercharged by Pineapple M3 Pro.', 1999.99, 1, 3),
       ('PineBook Pro 16', 'PABP160013', 'Supercharged by Pineapple M3 Max.', 2499.99, 1, 1),
       ('PineStudio Display', 'PASDISP014', 'Beauty in sight. Sound to match.', 1599.99, 1, 3),
       ('PinePencil (2nd generation)', 'PAPC200015', 'Dream it. Jot it. Draw it.', 129.99, 1, 3),
       ('PineMagic Keyboard', 'PAMKEYB016', 'A fantastic typing experience.', 299.99, 1, 1);

-- Associer les produits aux étiquettes (product_label)
INSERT INTO product_label(product_id, label_id)
VALUES (1, 1),  -- PineApple Watch SE - Pineapple Deals
       (1, 7),  -- PineApple Watch SE - Pineapple Essentials
       (2, 4),  -- PinePhone 15 - Pineapple Friday
       (2, 6),  -- PinePhone 15 - Back to the Grove
       (3, 1),  -- PinePad 10.2-inch - Pineapple Deals
       (3, 3),  -- PinePad 10.2-inch - Winter Sun Specials
       (4, 4),  -- PinePad Pro 12.9-inch - Pineapple Friday
       (4, 8),  -- PinePad Pro 12.9-inch - Limited Edition
       (5, 1),  -- PinePods - Pineapple Deals
       (5, 2),  -- PinePods - Tropical Holidays
       (6, 1),  -- PineBook - Pineapple Deals
       (6, 6),  -- PineBook - Back to the Grove
       (7, 5),  -- PineVision - Golden Harvest
       (8, 1),  -- PineApple Watch Ultra 2 - Pineapple Deals
       (8, 8),  -- PineApple Watch Ultra 2 - Limited Edition
       (9, 4),  -- PinePhone 15 Pro Max - Pineapple Friday
       (9, 5),  -- PinePhone 15 Pro Max - Golden Harvest
       (10, 1), -- PinePad mini - Pineapple Deals
       (10, 3), -- PinePad mini - Winter Sun Specials
       (11, 1), -- PinePods Max - Pineapple Deals
       (11, 2), -- PinePods Max - Tropical Holidays
       (12, 4), -- PineBook Pro 14 - Pineapple Friday
       (12, 8), -- PineBook Pro 14 - Limited Edition
       (13, 4), -- PineBook Pro 16 - Pineapple Friday
       (13, 8), -- PineBook Pro 16 - Limited Edition
       (14, 7), -- PineStudio Display - Pineapple Essentials
       (15, 7), -- PinePencil (2nd generation) - Pineapple Essentials
       (16, 7); -- PineMagic Keyboard - Pineapple Essentials

INSERT INTO purchase (date)
VALUES ("2025-03-11");

INSERT INTO purchase_line (purchase_id, quantity, purchase_price, product_id)
VALUES (1, 2, 1500, 4);