package com.example.studentsmoney.enums;

/**
 * This is a enum for working with Hubs.
 * <p>
 * We have 3 types:
 * <ul>
 *     <li>{@link Type#income} - user's source of money;</li>
 *     <li>{@link Type#asset} - user's account, where he can hold his money;</li>
 *     <li>{@link Type#spend} - on what user has spend his money.</li>
 * </ul>
 *
 * @author MarkYav
 * @version 1.0
 * @since 2021-01-25
 */
public enum Type {
    income,
    asset,
    spend
}
