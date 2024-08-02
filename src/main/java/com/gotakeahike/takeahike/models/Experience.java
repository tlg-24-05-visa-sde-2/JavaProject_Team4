package com.gotakeahike.takeahike.models;


/**
 * Enum representing different levels of experience required for a trail.
 * This enum is used to categorize trails based on the experience level needed.
 * We created a formula involving Enums in our SaveTrailToUser() function in UserService
 *  which further defined which ENUM stood for which distance in mails when paring with Trails.
 */
public enum Experience {

    BEGINNER,
    INTERMEDIATE,
    ADVANCED;
}