-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 20-01-2019 a las 20:07:03
-- Versión del servidor: 10.1.36-MariaDB
-- Versión de PHP: 5.6.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `calculadorasoap`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operacion`
--

CREATE TABLE `operacion` (
  `id` int(11) NOT NULL,
  `intA` int(11) DEFAULT NULL,
  `intB` int(11) DEFAULT NULL,
  `intCAdd` int(11) DEFAULT NULL,
  `intCSub` int(11) DEFAULT NULL,
  `intCMul` int(11) DEFAULT NULL,
  `intCDiv` int(11) DEFAULT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `operacion`
--

INSERT INTO `operacion` (`id`, `intA`, `intB`, `intCAdd`, `intCSub`, `intCMul`, `intCDiv`, `fecha`) VALUES
(1, 3, 6, 9, -3, 18, 0, '2019-01-20'),
(2, 6, 0, 6, 6, 0, NULL, '2019-01-20'),
(3, 7, 30, 37, -23, 210, 0, '2019-01-20'),
(4, 7, 2, 9, 5, 14, 4, '2019-01-20'),
(6, 7, 4, 11, 3, 28, 2, '2019-01-20'),
(7, 6, 8, 14, -2, 48, 1, '2019-01-20'),
(8, 9, 4, 13, 5, 36, 2, '2019-01-20'),
(9, 10, 7, 17, 3, 70, 1, '2019-01-20'),
(10, 23, 2, 25, 21, 46, 12, '2019-01-20'),
(11, 3, 4, 7, -1, 12, 1, '2019-01-20'),
(12, 3, 4, 7, -1, 12, 1, '2019-01-20'),
(13, 5, 3, 8, 2, 15, 2, '2019-01-20'),
(14, 6, 8, 14, -2, 48, 1, '2019-01-20'),
(15, 2, 5, 7, -3, 10, 0, '2019-01-20'),
(16, 7, 3, 10, 4, 21, 2, '2019-01-20'),
(17, 3, 7, 10, -4, 21, 0, '2019-01-20'),
(18, 9, 3, 12, 6, 27, 3, '2019-01-20'),
(19, 5, 7, 12, -2, 35, 1, '2019-01-20'),
(20, 7, 0, 7, 7, 0, NULL, '2019-01-20'),
(21, 3, 2, 5, 1, 6, 2, '2019-01-20'),
(22, 9, 4, 13, 5, 36, 2, '2019-01-20'),
(23, 3, 5, 8, -2, 15, 1, '2019-01-20'),
(24, 8, 3, 11, 5, 24, 3, '2019-01-20'),
(25, 9, 2, 11, 7, 18, 4, '2019-01-20'),
(26, 8, 9, 17, -1, 72, 1, '2019-01-20'),
(27, 7, 6, 13, 1, 42, 1, '2019-01-20'),
(28, 4, 5, 9, -1, 20, 1, '2019-01-20'),
(29, 7, 4, 11, 3, 28, 2, '2019-01-20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `street` varchar(100) NOT NULL,
  `suite` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `zipcode` varchar(100) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `phone` varchar(50) NOT NULL,
  `website` varchar(100) NOT NULL,
  `company_name` varchar(100) NOT NULL,
  `catchPhrase` varchar(100) NOT NULL,
  `bs` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `email`, `street`, `suite`, `city`, `zipcode`, `lat`, `lng`, `phone`, `website`, `company_name`, `catchPhrase`, `bs`) VALUES
(1, 'Leanne Graham', 'Bret', 'Sincere@april.biz', 'Kulas Light', 'Apt. 556', 'Gwenborough', '92998-3874', -37.3159, 81.1496, '1-770-736-8031 x56442', 'hildegard.org', 'Romaguera-Crona', 'Multi-layered client-server neural-net', 'harness real-time e-markets'),
(2, 'Ervin Howell', 'Antonette', 'Shanna@melissa.tv', 'Victor Plains', 'Suite 879', 'Wisokyburgh', '90566-7771', -43.9509, -34.4618, '010-692-6593 x09125', 'anastasia.net', 'Deckow-Crist', 'Proactive didactic contingency', 'synergize scalable supply-chains'),
(3, 'Clementine Bauch', 'Samantha', 'Nathan@yesenia.net', 'Douglas Extension', 'Suite 847', 'McKenziehaven', '59590-4157', -68.6102, -47.0653, '1-463-123-4447', 'ramiro.info', 'Romaguera-Jacobson', 'Face to face bifurcated interface', 'e-enable strategic applications'),
(4, 'Patricia Lebsack', 'Karianne', 'Julianne.OConner@kory.org', 'Hoeger Mall', 'Apt. 692', 'South Elvis', '53919-4257', 29.4572, -164.299, '493-170-9623 x156', 'kale.biz', 'Robel-Corkery', 'Multi-tiered zero tolerance productivity', 'transition cutting-edge web services'),
(5, 'Chelsey Dietrich', 'Kamren', 'Lucio_Hettinger@annie.ca', 'Skiles Walks', 'Suite 351', 'Roscoeview', '33263', -31.8129, 62.5342, '(254)954-1289', 'demarco.info', 'Keebler LLC', 'User-centric fault-tolerant solution', 'revolutionize end-to-end systems'),
(6, 'Mrs. Dennis Schulist', 'Leopoldo_Corkery', 'Karley_Dach@jasper.info', 'Norberto Crossing', 'Apt. 950', 'South Christy', '23505-1337', -71.4197, 71.7478, '1-477-935-8478 x6430', 'ola.org', 'Considine-Lockman', 'Synchronised bottom-line interface', 'e-enable innovative applications'),
(7, 'Kurtis Weissnat', 'Elwyn.Skiles', 'Telly.Hoeger@billy.biz', 'Rex Trail', 'Suite 280', 'Howemouth', '58804-1099', 24.8918, 21.8984, '210.067.6132', 'elvis.io', 'Johns Group', 'Configurable multimedia task-force', 'generate enterprise e-tailers'),
(8, 'Nicholas Runolfsdottir V', 'Maxime_Nienow', 'Sherwood@rosamond.me', 'Ellsworth Summit', 'Suite 729', 'Aliyaview', '45169', -14.399, -120.7677, '586.493.6943 x140', 'jacynthe.com', 'Abernathy Group', 'Implemented secondary concept', 'e-enable extensible e-tailers'),
(9, 'Glenna Reichert', 'Delphine', 'Chaim_McDermott@dana.io', 'Dayna Park', 'Suite 449', 'Bartholomebury', '76495-3109', 24.6463, -168.8889, '(775)976-6794 x41206', 'conrad.com', 'Yost and Sons', 'Switchable contextually-based project', 'aggregate real-time technologies'),
(10, 'Clementina DuBuque', 'Moriah.Stanton', 'Rey.Padberg@karina.biz', 'Kattie Turnpike', 'Suite 198', 'Lebsackbury', '31428-2261', -38.2386, 57.2232, '024-648-3804', 'ambrose.net', 'Hoeger LLC', 'Centralized empowering task-force', 'target end-to-end models');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `operacion`
--
ALTER TABLE `operacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `operacion`
--
ALTER TABLE `operacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
