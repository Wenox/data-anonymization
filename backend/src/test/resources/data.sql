INSERT INTO users (id, email, password, role, first_name, last_name, purpose, status, verified, marked_for_removal,
                   force_removal)
values ('1', 'admin@admin.com', '$2a$10$j5gTc1j8znjtjHbdS8f0Pu4sEVPze0wcvLQsb7ZYAYeXGLsceRDMK', 'ADMIN', 'Admin Firstname', 'Admin Lastname', 'Admin Purpose', 0, true, false,
        false);

INSERT INTO users (id, email, password, role, first_name, last_name, purpose, status, verified, marked_for_removal,
                   force_removal)
values ('2', 'user@user.com', '$2a$10$b7lKhM8.jtCTana.C99nzeLwXNl87E1RCjbR/5Cq8HNff4mYuodZq', 'VERIFIED_USER', 'User Firstname', 'User Lastname', 'User Purpose', 0, true, false, false);