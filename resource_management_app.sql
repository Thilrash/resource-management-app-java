-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2021 at 10:33 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `resource_management_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `add_working_days`
--

CREATE TABLE `add_working_days` (
  `id` int(11) NOT NULL,
  `no_working_days` int(11) NOT NULL,
  `hours` int(11) NOT NULL,
  `minutes` int(11) NOT NULL,
  `monday` tinyint(1) NOT NULL,
  `tuesday` tinyint(1) NOT NULL,
  `wednesday` tinyint(1) NOT NULL,
  `thursday` tinyint(1) NOT NULL,
  `friday` tinyint(1) NOT NULL,
  `saturday` tinyint(1) NOT NULL,
  `sunday` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `add_working_days`
--

INSERT INTO `add_working_days` (`id`, `no_working_days`, `hours`, `minutes`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`) VALUES
(1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `lecturer`
--

CREATE TABLE `lecturer` (
  `id` int(11) NOT NULL,
  `lecturer_name` varchar(40) NOT NULL,
  `employee_id` char(6) NOT NULL,
  `faculty` varchar(40) NOT NULL,
  `department` varchar(40) NOT NULL,
  `center` varchar(40) NOT NULL,
  `building` varchar(40) NOT NULL,
  `level` char(1) NOT NULL,
  `rank` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lecturer`
--

INSERT INTO `lecturer` (`id`, `lecturer_name`, `employee_id`, `faculty`, `department`, `center`, `building`, `level`, `rank`) VALUES
(1, 'Mr. Joseph Kemp', '000001', 'Faculty of Computing', 'Information Technology', 'Malabe', 'Main Building', '1', '1.000001'),
(2, 'Mr. Kallis Kemp', '000002', 'Faculty of Business', 'Accounting & Finance', 'Malabe', 'B-Block', '2', '2.000002'),
(3, 'Mrs. Arisa Millon', '000003', 'Faculty of Computing', 'Software Engineering', 'Malabe', 'Main Building', '2', '2.000003'),
(4, 'Mr. One', '000004', 'Faculty of Architecture', 'Main Architecture Department', 'Jaffna', 'B-Block', '2', '2.000004'),
(5, 'Mr. Lecturer', '000005', 'Faculty of Computing', 'Information Technology', 'Malabe', 'Main Building', '5', '5.000005'),
(6, 'Mrs. Professor', '000006', 'Faculty of Business', 'Accounting & Finance', 'Malabe', 'Main Building', '1', '1.000006'),
(7, 'Mrs. Kavinayake', '000007', 'Faculty of Humanities & Sciences', 'Bio Technology', 'Matara', 'A-Block', '3', '3.000007'),
(8, 'Mr. Kevin Jeroshan', '000008', 'Faculty of Business', 'Quality Management', 'Kurunagala', 'B-Block', '4', '4.000008'),
(9, 'Ms. Revathy Krishnan', '000009', 'Faculty of Engineering', 'Material', 'Kandy', 'C-Block', '1', '1.000009'),
(10, 'Mr. Thilrash Ameen', '000010', 'Faculty of Computing', 'Information Technology', 'Malabe', 'Main Building', '1', '1.000010'),
(11, 'Ms. Thashneekah Ahsan', '000011', 'Faculty of Humanities & Sciences', 'Physical Sciences', 'Malabe', 'C-Block', '3', '3.000011');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `id` int(11) NOT NULL,
  `building_name` varchar(40) NOT NULL,
  `room_name` varchar(40) NOT NULL,
  `room_type` varchar(40) NOT NULL,
  `capacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`id`, `building_name`, `room_name`, `room_type`, `capacity`) VALUES
(1, 'D-Block', 'D-A100', 'Lecture Hall', 500),
(2, 'D-Block', 'D-B100', 'Laboratory', 350),
(3, 'Main building', 'M-A100', 'Lecture Hall', 250),
(4, 'A-Block', 'A-A110', 'Lecture Hall', 100),
(5, 'A-Block', 'A-A120', 'Lecture Hall', 105),
(6, 'A-Block', 'A-A130', 'Lecture Hall', 90),
(7, 'A-Block', 'A-A140', 'Lecture Hall', 97),
(8, 'A-Block', 'A-A150', 'Lecture Hall', 50),
(9, 'A-Block', 'A-B100', 'Laboratory', 40),
(10, 'D-Block', 'D-B110', 'Laboratory', 30),
(11, 'D-Block', 'D-B120', 'Laboratory', 25),
(12, 'D-Block', 'D-A120', 'Lecture Hall', 150),
(13, 'D-Block', 'D-A130', 'Lecture Hall', 100),
(14, 'D-Block', 'D-B130', 'Laboratory', 50),
(15, 'D-Block', 'D-A140', 'Lecture Hall', 200),
(16, 'D-Block', 'D-B140', 'Laboratory', 60),
(17, 'D-Block', 'D-A150', 'Lecture Hall', 235),
(18, 'D-Block', 'D-B150', 'Laboratory', 55),
(19, 'Main Building', 'M-B100', 'Laboratory', 80),
(20, 'Main Building', 'M-A200', 'Lecture Hall', 400),
(21, 'Main Building', 'M-B200', 'Laboratory', 60),
(22, 'Main Building', 'M-A300', 'Lecture Hall', 600);

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `lecturer1` varchar(40) NOT NULL,
  `lecturer2` varchar(40) DEFAULT NULL,
  `subject_code` varchar(20) NOT NULL,
  `subject_name` varchar(50) NOT NULL,
  `group_id` varchar(20) NOT NULL,
  `tag` varchar(20) NOT NULL,
  `no_of_students` int(11) NOT NULL,
  `duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`id`, `lecturer1`, `lecturer2`, `subject_code`, `subject_name`, `group_id`, `tag`, `no_of_students`, `duration`) VALUES
(1, 'Mr. Joseph Kemp', NULL, 'IT1020', 'MC', 'Y1.S1.IT.1', 'Lecture', 300, 2),
(2, 'Mr. Kallis Kemp', 'Mrs. Kavinayake', 'IT1010', 'CF', 'Y1.S1.IT.1.1', 'Practical', 600, 2),
(3, 'Mr. Kevin Jeroshan', NULL, 'IT2020', 'OOP', 'Y1.S1.IT.1', 'Tutorial', 400, 2),
(4, 'Mr. Thilrash Ameen', NULL, 'IT2020', 'OOP', 'Y1.S1.IT.2', 'Lecture', 500, 3),
(5, 'Ms. Thashneekah Ahsan', 'Mrs. Kavinayake', 'IT1040', 'IN', 'Y1.S1.IT.1.3', 'Practical', 500, 2),
(6, 'Mrs. Kavinayake', NULL, 'IT2010', 'IP', 'Y1.S1.IT.1.7', 'Practical', 200, 2),
(7, 'Mrs. Arisa Millon', NULL, 'IT1030', 'DS', 'Y1.S1.IT.1', 'Lecture', 150, 2),
(8, 'Ms. Revathy Krishnan', 'Mr. Kevin Jeroshan', 'IT2020', 'OOP', 'Y1.S1.IT.1.3', 'Practical', 500, 2),
(9, 'Mr. Thilrash Ameen', NULL, 'IT1030', 'DS', 'Y1.S1.IT.2', 'Lecture', 650, 2),
(10, 'Mrs. Professor', 'Mr. Lecturer', 'IT2010', 'IP', 'Y1.S1.IT.1.9', 'Practical', 400, 2),
(11, 'Mrs. Arisa Millon', 'Mr. Joseph Kemp', 'IT2010', 'IP', 'Y1.S1.IT.2', 'Lecture', 500, 2),
(12, 'Mr. Thilrash Ameen', 'Ms. Revathy Krishnan', 'IT1020', 'MC', 'Y1.S1.IT.1.7', 'Practical', 450, 2),
(13, 'Mr. One', 'Mrs. Professor', 'IT1010', 'CF', 'Y1.S1.IT.2', 'Tutorial', 500, 1),
(14, 'Mr. Kevin Jeroshan', NULL, 'IT2020', 'OOP', 'Y1.S1.IT.1', 'Tutorial', 100, 1),
(15, 'Mr. Thilrash Ameen', 'Mr. Lecturer', 'IT2020', 'OOP', 'Y1.S1.IT.2', 'Lecture', 250, 2),
(16, 'Ms. Revathy Krishnan', 'Mr. Kallis Kemp', 'IT1040', 'IN', 'Y1.S1.IT.1', 'Lecture', 300, 2);

-- --------------------------------------------------------

--
-- Table structure for table `student_group`
--

CREATE TABLE `student_group` (
  `id` int(11) NOT NULL,
  `acadedemic_year` char(5) NOT NULL,
  `programme` varchar(20) NOT NULL,
  `group_no` int(11) NOT NULL,
  `sub_group_no` int(11) NOT NULL,
  `group_id` varchar(20) NOT NULL,
  `sub_group_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_group`
--

INSERT INTO `student_group` (`id`, `acadedemic_year`, `programme`, `group_no`, `sub_group_no`, `group_id`, `sub_group_id`) VALUES
(1, 'Y1.S1', 'IT', 1, 1, 'Y1.S1.IT.1', 'Y1.S1.IT.1.1'),
(2, 'Y1.S1', 'IT', 1, 2, 'Y1.S1.IT.1', 'Y1.S1.IT.1.2'),
(3, 'Y1.S1', 'IT', 1, 3, 'Y1.S1.IT.1', 'Y1.S1.IT.1.3'),
(4, 'Y1.S1', 'IT', 1, 4, 'Y1.S1.IT.1', 'Y1.S1.IT.1.4'),
(5, 'Y1.S1', 'IT', 1, 5, 'Y1.S1.IT.1', 'Y1.S1.IT.1.5'),
(6, 'Y1.S1', 'IT', 1, 6, 'Y1.S1.IT.1', 'Y1.S1.IT.1.6'),
(7, 'Y1.S1', 'IT', 1, 7, 'Y1.S1.IT.1', 'Y1.S1.IT.1.7'),
(8, 'Y1.S1', 'IT', 1, 8, 'Y1.S1.IT.1', 'Y1.S1.IT.1.8'),
(9, 'Y1.S1', 'IT', 1, 9, 'Y1.S1.IT.1', 'Y1.S1.IT.1.9'),
(10, 'Y1.S1', 'IT', 1, 10, 'Y1.S1.IT.1', 'Y1.S1.IT.1.10'),
(11, 'Y1.S1', 'IT', 2, 1, 'Y1.S1.IT.2', 'Y1.S1.IT.2.1'),
(12, 'Y1.S1', 'IT', 2, 2, 'Y1.S1.IT.2', 'Y1.S1.IT.2.2'),
(13, 'Y1.S1', 'IT', 2, 3, 'Y1.S1.IT.2', 'Y1.S1.IT.2.3'),
(14, 'Y1.S1', 'IT', 2, 4, 'Y1.S1.IT.2', 'Y1.S1.IT.2.4');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `id` int(11) NOT NULL,
  `offered_year` char(1) NOT NULL,
  `offered_semester` char(1) NOT NULL,
  `subject_name` varchar(50) NOT NULL,
  `subject_code` varchar(50) NOT NULL,
  `no_lecture_hours` int(11) NOT NULL,
  `no_tutorial_hours` int(11) NOT NULL,
  `no_lab_hours` int(11) NOT NULL,
  `no_evaluvation_hours` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`id`, `offered_year`, `offered_semester`, `subject_name`, `subject_code`, `no_lecture_hours`, `no_tutorial_hours`, `no_lab_hours`, `no_evaluvation_hours`) VALUES
(1, '1', '1', 'MC', 'IT1020', 9, 5, 4, 5),
(2, '1', '1', 'CF', 'IT1010', 2, 1, 2, 2),
(3, '1', '1', 'DS', 'IT1030', 2, 1, 2, 3),
(4, '1', '1', 'IN', 'IT1040', 2, 1, 2, 2),
(5, '1', '2', 'IP', 'IT2010', 2, 1, 2, 4),
(6, '1', '2', 'OOP', 'IT2020', 2, 1, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `tag_name` varchar(25) NOT NULL,
  `tag_code` varchar(25) NOT NULL,
  `related_tag` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`id`, `tag_name`, `tag_code`, `related_tag`) VALUES
(1, 'Lecture', 'L', 'Lecture'),
(2, 'Tutorial', 'T', 'Tutorial'),
(3, 'Practical', 'P', 'Practical');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `add_working_days`
--
ALTER TABLE `add_working_days`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lecturer`
--
ALTER TABLE `lecturer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student_group`
--
ALTER TABLE `student_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lecturer`
--
ALTER TABLE `lecturer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `student_group`
--
ALTER TABLE `student_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tag`
--
ALTER TABLE `tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
