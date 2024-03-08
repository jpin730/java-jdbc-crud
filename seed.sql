USE db;
DROP TABLE IF EXISTS employees;
CREATE TABLE `employees` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(64) DEFAULT NULL,
    `last_name` varchar(64) DEFAULT NULL,
    `email` varchar(64) DEFAULT NULL,
    `salary` DECIMAL (10, 2) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = latin1;
INSERT INTO `employees` (
        `first_name`,
        `last_name`,
        `email`,
        `salary`
    )
VALUES(
        'John',
        'Doe',
        'john.doe@example.com',
        50000.00
    ),
    (
        'Jane',
        'Smith',
        'jane.smith@example.com',
        60000.00
    ),
    (
        'Michael',
        'Johnson',
        'michael.johnson@example.com',
        55000.00
    ),
    (
        'Emily',
        'Brown',
        'emily.brown@example.com',
        52000.00
    ),
    (
        'David',
        'Davis',
        'david.davis@example.com',
        58000.00
    );