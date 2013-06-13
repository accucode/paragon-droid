/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.utility;

import com.kodemore.collection.KmList;

public class KmSamples
{
    public static KmList<String> getStrings(String prefix, int n)
    {
        KmList<String> v = new KmList<String>();

        for ( int i = 0; i < n; i++ )
            v.add(prefix + " " + i);

        return v;
    }

    public static KmList<String> getRandomAnimalsDescription(int n)
    {
        KmList<String> v = new KmList<String>();

        for ( int i = 0; i < n; i++ )
            v.add(getRandomAnimalDescription());

        return v;
    }

    /**
     * Get a random animal, with adjective and color.
     * E.g.: short red dog.
     */
    public static String getRandomAnimalDescription()
    {
        return Kmu.format(
            "%s %s %s",
            getAdjectives().getRandom(),
            getColorsNames().getRandom(),
            getAnimalTypes().getRandom());
    }

    public static KmList<String> getAnimalTypes()
    {
        String[] arr =
        {
            "alligator",
            "ant",
            "ape",
            "baboon",
            "badger",
            "bat",
            "bison",
            "boar",
            "buffalo",
            "butterfly",
            "camel",
            "cat",
            "cheetah",
            "chicken",
            "clam",
            "crane",
            "crow",
            "deer",
            "dog",
            "dolphin",
            "donkey",
            "dove",
            "eagle",
            "elephant",
            "elk",
            "falcon",
            "fish",
            "fly",
            "fox",
            "frog",
            "giraffe",
            "gnat",
            "goat",
            "goose",
            "gorilla",
            "hamster",
            "hawk",
            "heron",
            "hornet",
            "horse",
            "hyena",
            "iguana",
            "jackal",
            "jellyfish",
            "kangaroo",
            "koala",
            "lark",
            "lemur",
            "leopard",
            "lion",
            "llama",
            "lobster",
            "magpie",
            "mallard",
            "marten",
            "mink",
            "mole",
            "monkey",
            "moose",
            "mouse",
            "mule",
            "newt",
            "nightingale",
            "octopus",
            "ostrich",
            "otter",
            "owl",
            "ox",
            "panda",
            "panther",
            "parrot",
            "pig",
            "quail",
            "rabbit",
            "raccoon",
            "ram",
            "rat",
            "raven",
            "salmon",
            "sardine",
            "seal",
            "shark",
            "sheep",
            "skunk",
            "snake",
            "spider",
            "squirrel",
            "swan",
            "termite",
            "tiger",
            "toad",
            "trout",
            "turkey",
            "turtle",
            "viper",
            "vulture",
            "walrus",
            "weasel",
            "whale",
            "wolf",
            "worm",
            "yak",
            "zebra"
        };

        KmList<String> v;
        v = new KmList<String>();
        v.addAll(arr);
        v.sort();
        return v;
    }

    public static KmList<String> getColorsNames()
    {
        String[] arr =
        {
            "white",
            "silver",
            "gray",
            "black",
            "red",
            "maroon",
            "yellow",
            "lime",
            "green",
            "aqua",
            "teal",
            "blue",
            "navy",
            "purple",
            "brown",
            "beige",
            "tan"
        };

        KmList<String> v;
        v = new KmList<String>();
        v.addAll(arr);
        v.sort();
        return v;
    }

    public static KmList<String> getAdjectives()
    {
        String[] arr =
        {
            "big",
            "large",
            "short",
            "fast",
            "good",
            "old",
            "pretty",
            "fat",
            "happy",
            "funny",
            "healthy",
            "top",
            "friendly",
            "small",
            "cold",
            "dangerous",
            "weak",
            "clean",
            "thirsty",
            "sick",
            "soft",
            "strong",
            "tired",
            "little",
            "slow",
            "dirty"
        };

        KmList<String> v;
        v = new KmList<String>();
        v.addAll(arr);
        v.sort();
        return v;
    }
}
