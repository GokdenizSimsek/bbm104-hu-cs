import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        FileOutput.writeToFile("output.txt","",false,false);
        String file_name_1 = args[0];
        String file_name_2 = args[1];
        String[] board = FileInput.readFile(file_name_1, true, true);
        String[] moves = FileInput.readFile(file_name_2, true, true);

        List<List<String>> myBoard = new ArrayList<>();
        int row = 0;
        FileOutput.writeToFile("output.txt","Game board:\n",true,false);
        for (String boards : board) {
            FileOutput.writeToFile("output.txt",boards + "\n",true,false);
            List<String> board_list = Arrays.asList(boards.split(" "));
            myBoard.add(board_list);
            row = board_list.size() - 1;
        }
        int column = myBoard.size() - 1;
        FileOutput.writeToFile("output.txt","\nYour movement is:\n",true,false);
        int points = 0;
        for (String move : moves) {
            List<String> move_1 = Arrays.asList(move.split(" "));
            for (String move_2 : move_1) {
                int index1 = 0;
                for (List<String> boards : myBoard) {
                    if (boards.contains("*")) {
                        break;
                    } else {
                        index1++;
                    }
                }
                int index2 = myBoard.get(index1).indexOf("*");
                FileOutput.writeToFile("output.txt",move_2 + " ",true,false);
                if (move_2.equals("R")) {
                    if (index2 != row) {
                        if (myBoard.get(index1).get(index2 + 1).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(index1).get(index2 + 1).equals("W")) {
                            if (index2 != 0) {
                                if (myBoard.get(index1).get(index2 - 1).equals("H")) {
                                    myBoard.get(index1).set(index2, " ");
                                    break;
                                } else {
                                    if (myBoard.get(index1).get(index2 - 1).equals("R")) {
                                        points = points + 10;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1).set(index2 - 1, "*");
                                    } else if (myBoard.get(index1).get(index2 - 1).equals("Y")) {
                                        points = points + 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1).set(index2 - 1, "*");
                                    } else if (myBoard.get(index1).get(index2 - 1).equals("B")) {
                                        points = points - 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1).set(index2 - 1, "*");
                                    } else {
                                        myBoard.get(index1).set(index2, myBoard.get(index1).get(index2 - 1));
                                        myBoard.get(index1).set(index2 - 1, "*");
                                    }
                                }
                            } else {
                                if (myBoard.get(index1).get(row).equals("R")) {
                                    points = points + 10;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1).set(row, "*");
                                } else if (myBoard.get(index1).get(row).equals("Y")) {
                                    points = points + 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1).set(row, "*");
                                } else if (myBoard.get(index1).get(row).equals("B")) {
                                    points = points - 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1).set(row, "*");
                                } else {
                                    myBoard.get(index1).set(index2, myBoard.get(index1).get(row));
                                    myBoard.get(index1).set(row, "*");
                                }
                            }
                        } else {
                            if (myBoard.get(index1).get(index2 + 1).equals("R")) {
                                points = points + 10;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 + 1, "*");
                            } else if (myBoard.get(index1).get(index2 + 1).equals("Y")) {
                                points = points + 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 + 1, "*");
                            } else if (myBoard.get(index1).get(index2 + 1).equals("B")) {
                                points = points - 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 + 1, "*");
                            } else {
                                myBoard.get(index1).set(index2, myBoard.get(index1).get(index2 + 1));
                                myBoard.get(index1).set(index2 + 1, "*");
                            }
                        }
                    } else {
                        if (myBoard.get(index1).get(0).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(index1).get(0).equals("W")) {
                            if (myBoard.get(index1).get(index2 - 1).equals("H")) {
                                myBoard.get(index1).set(index2, " ");
                                break;
                            } else if (myBoard.get(index1).get(index2 - 1).equals("R")) {
                                points = points + 10;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 - 1, "*");
                            } else if (myBoard.get(index1).get(index2 - 1).equals("Y")) {
                                points = points + 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 - 1, "*");
                            } else if (myBoard.get(index1).get(index2 - 1).equals("B")) {
                                points = points - 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 - 1, "*");
                            } else {
                                myBoard.get(index1).set(index2, myBoard.get(index1).get(index2 - 1));
                                myBoard.get(index1).set(index2 - 1, "*");
                            }
                        } else if (myBoard.get(index1).get(0).equals("R")) {
                            points = points + 10;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(index1).set(0, "*");
                        } else if (myBoard.get(index1).get(0).equals("Y")) {
                            points = points + 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(index1).set(0, "*");
                        } else if (myBoard.get(index1).get(0).equals("B")) {
                            points = points - 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(index1).set(0, "*");
                        } else {
                            myBoard.get(index1).set(index2, myBoard.get(index1).get(0));
                            myBoard.get(index1).set(0, "*");
                        }
                    }
                } else if (move_2.equals("L")) {
                    if (index2 != 0) {
                        if (myBoard.get(index1).get(index2 - 1).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(index1).get(index2 - 1).equals("W")) {
                            if (index2 != row) {
                                if (myBoard.get(index1).get(index2 + 1).equals("H")) {
                                    myBoard.get(index1).set(index2, " ");
                                    break;
                                } else {
                                    if (myBoard.get(index1).get(index2 + 1).equals("R")) {
                                        points = points + 10;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1).set(index2 + 1, "*");
                                    } else if (myBoard.get(index1).get(index2 + 1).equals("Y")) {
                                        points = points + 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1).set(index2 + 1, "*");
                                    } else if (myBoard.get(index1).get(index2 + 1).equals("B")) {
                                        points = points - 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1).set(index2 + 1, "*");
                                    } else {
                                        myBoard.get(index1).set(index2, myBoard.get(index1).get(index2 + 1));
                                        myBoard.get(index1).set(index2 + 1, "*");
                                    }
                                }
                            } else {
                                if (myBoard.get(index1).get(0).equals("R")) {
                                    points = points + 10;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1).set(0, "*");
                                } else if (myBoard.get(index1).get(0).equals("Y")) {
                                    points = points + 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1).set(0, "*");
                                } else if (myBoard.get(index1).get(0).equals("B")) {
                                    points = points - 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1).set(0, "*");
                                } else {
                                    myBoard.get(index1).set(index2, myBoard.get(index1).get(0));
                                    myBoard.get(index1).set(0, "*");
                                }
                            }
                        } else {
                            if (myBoard.get(index1).get(index2 - 1).equals("R")) {
                                points = points + 10;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 - 1, "*");
                            } else if (myBoard.get(index1).get(index2 - 1).equals("Y")) {
                                points = points + 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 - 1, "*");
                            } else if (myBoard.get(index1).get(index2 - 1).equals("B")) {
                                points = points - 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 - 1, "*");
                            } else {
                                myBoard.get(index1).set(index2, myBoard.get(index1).get(index2 - 1));
                                myBoard.get(index1).set(index2 - 1, "*");
                            }
                        }
                    } else {
                        if (myBoard.get(index1).get(row).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(index1).get(row).equals("W")) {
                            if (myBoard.get(index1).get(index2 + 1).equals("H")) {
                                myBoard.get(index1).set(index2, " ");
                                break;
                            }else if (myBoard.get(index1).get(index2 + 1).equals("R")) {
                                points = points + 10;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 + 1, "*");
                            } else if (myBoard.get(index1).get(index2 + 1).equals("Y")) {
                                points = points + 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 + 1, "*");
                            } else if (myBoard.get(index1).get(index2 + 1).equals("B")) {
                                points = points - 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1).set(index2 + 1, "*");
                            } else {
                                myBoard.get(index1).set(index2, myBoard.get(index1).get(index2 + 1));
                                myBoard.get(index1).set(index2 + 1, "*");
                            }
                        } else if (myBoard.get(index1).get(row).equals("R")) {
                            points = points + 10;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(index1).set(row, "*");
                        } else if (myBoard.get(index1).get(row).equals("Y")) {
                            points = points + 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(index1).set(row, "*");
                        } else if (myBoard.get(index1).get(row).equals("B")) {
                            points = points - 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(index1).set(row, "*");
                        } else {
                            myBoard.get(index1).set(index2, myBoard.get(index1).get(row));
                            myBoard.get(index1).set(row, "*");
                        }
                    }
                } else if (move_2.equals("U")) {
                    if (index1 != 0) {
                        if (myBoard.get(index1 - 1).get(index2).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(index1 - 1).get(index2).equals("W")) {
                            if (index1 != column) {
                                if (myBoard.get(index1 + 1).get(index2).equals("H")) {
                                    myBoard.get(index1).set(index2, " ");
                                    break;
                                } else {
                                    if (myBoard.get(index1 + 1).get(index2).equals("R")) {
                                        points = points + 10;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1 + 1).set(index2, "*");
                                    } else if (myBoard.get(index1 + 1).get(index2).equals("Y")) {
                                        points = points + 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1 + 1).set(index2, "*");
                                    } else if (myBoard.get(index1 + 1).get(index2).equals("B")) {
                                        points = points - 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1 + 1).set(index2, "*");
                                    } else {
                                        myBoard.get(index1).set(index2, myBoard.get(index1 + 1).get(index2));
                                        myBoard.get(index1 + 1).set(index2, "*");
                                    }
                                }
                            } else {
                                if (myBoard.get(0).get(index2).equals("R")) {
                                    points = points + 10;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(0).set(index2, "*");
                                } else if (myBoard.get(0).get(index2).equals("Y")) {
                                    points = points + 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(0).set(index2, "*");
                                } else if (myBoard.get(0).get(index2).equals("B")) {
                                    points = points - 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(0).set(index2, "*");
                                } else {
                                    myBoard.get(index1).set(index2, myBoard.get(0).get(index2));
                                    myBoard.get(0).set(index2, "*");
                                }
                            }
                        } else {
                            if (myBoard.get(index1 - 1).get(index2).equals("R")) {
                                points = points + 10;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1 - 1).set(index2, "*");
                            } else if (myBoard.get(index1 - 1).get(index2).equals("Y")) {
                                points = points + 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1 - 1).set(index2, "*");
                            } else if (myBoard.get(index1 - 1).get(index2).equals("B")) {
                                points = points - 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1 - 1).set(index2, "*");
                            } else {
                                myBoard.get(index1).set(index2, myBoard.get(index1 - 1).get(index2));
                                myBoard.get(index1 - 1).set(index2, "*");
                            }
                        }
                    } else {
                        if (myBoard.get(column).get(index2).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(column).get(index2).equals("W")) {
                            if (myBoard.get(index1 + 1).get(index2).equals("H")) {
                                myBoard.get(index1).set(index2, " ");
                                break;
                            } else {
                                if (myBoard.get(column).get(index2).equals("R")) {
                                    points = points + 10;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(column).set(index2, "*");
                                } else if (myBoard.get(column).get(index2).equals("Y")) {
                                    points = points + 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(column).set(index2, "*");
                                } else if (myBoard.get(column).get(index2).equals("B")) {
                                    points = points - 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(column).set(index2, "*");
                                } else {
                                    myBoard.get(index1).set(index2, myBoard.get(column).get(index2));
                                    myBoard.get(column).set(index2, "*");
                                }
                            }
                        } else if (myBoard.get(column).get(index2).equals("R")) {
                            points = points + 10;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(column).set(index2, "*");
                        } else if (myBoard.get(column).get(index2).equals("Y")) {
                            points = points + 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(column).set(index2, "*");
                        } else if (myBoard.get(column).get(index2).equals("B")) {
                            points = points - 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(column).set(index2, "*");
                        } else {
                            myBoard.get(index1).set(index2, myBoard.get(column).get(index2));
                            myBoard.get(column).set(index2, "*");
                        }
                    }
                } else {
                    if (index1 != column) {
                        if (myBoard.get(index1 + 1).get(index2).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(index1 + 1).get(index2).equals("W")) {
                            if (index1 != 0) {
                                if (myBoard.get(index1 - 1).get(index2).equals("H")) {
                                    myBoard.get(index1).set(index2, " ");
                                    break;
                                } else {
                                    if (myBoard.get(index1 - 1).get(index2).equals("R")) {
                                        points = points + 10;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1 - 1).set(index2, "*");
                                    } else if (myBoard.get(index1 - 1).get(index2).equals("Y")) {
                                        points = points + 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1 - 1).set(index2, "*");
                                    } else if (myBoard.get(index1 - 1).get(index2).equals("B")) {
                                        points = points - 5;
                                        myBoard.get(index1).set(index2, "X");
                                        myBoard.get(index1 - 1).set(index2, "*");
                                    } else {
                                        myBoard.get(index1).set(index2, myBoard.get(index1 - 1).get(index2));
                                        myBoard.get(index1 - 1).set(index2, "*");
                                    }
                                }
                            } else {
                                if (myBoard.get(column).get(index2).equals("R")) {
                                    points = points + 10;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(column).set(index2, "*");
                                } else if (myBoard.get(column).get(index2).equals("Y")) {
                                    points = points + 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(column).set(index2, "*");
                                } else if (myBoard.get(column).get(index2).equals("B")) {
                                    points = points - 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(column).set(index2, "*");
                                } else {
                                    myBoard.get(index1).set(index2, myBoard.get(column).get(index2));
                                    myBoard.get(column).set(index2, "*");
                                }
                            }
                        } else {
                            if (myBoard.get(index1 + 1).get(index2).equals("R")) {
                                points = points + 10;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1 + 1).set(index2, "*");
                            } else if (myBoard.get(index1 + 1).get(index2).equals("Y")) {
                                points = points + 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1 + 1).set(index2, "*");
                            } else if (myBoard.get(index1 + 1).get(index2).equals("B")) {
                                points = points - 5;
                                myBoard.get(index1).set(index2, "X");
                                myBoard.get(index1 + 1).set(index2, "*");
                            } else {
                                myBoard.get(index1).set(index2, myBoard.get(index1 + 1).get(index2));
                                myBoard.get(index1 + 1).set(index2, "*");
                            }
                        }
                    } else {
                        if (myBoard.get(0).get(index2).equals("H")) {
                            myBoard.get(index1).set(index2, " ");
                            break;
                        } else if (myBoard.get(0).get(index2).equals("W")) {
                            if (myBoard.get(index1 - 1).get(index2).equals("H")) {
                                myBoard.get(index1).set(index2, " ");
                                break;
                            } else {
                                if (myBoard.get(index1 - 1).get(index2).equals("R")) {
                                    points = points + 10;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1 - 1).set(index2, "*");
                                } else if (myBoard.get(index1 - 1).get(index2).equals("Y")) {
                                    points = points + 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1 - 1).set(index2, "*");
                                } else if (myBoard.get(index1 - 1).get(index2).equals("B")) {
                                    points = points - 5;
                                    myBoard.get(index1).set(index2, "X");
                                    myBoard.get(index1 - 1).set(index2, "*");
                                } else {
                                    myBoard.get(index1).set(index2, myBoard.get(index1 - 1).get(index2));
                                    myBoard.get(index1 - 1).set(index2, "*");
                                }
                            }
                        } else if (myBoard.get(0).get(index2).equals("R")) {
                            points = points + 10;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(0).set(index2, "*");
                        } else if (myBoard.get(0).get(index2).equals("Y")) {
                            points = points + 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(0).set(index2, "*");
                        } else if (myBoard.get(0).get(index2).equals("B")) {
                            points = points - 5;
                            myBoard.get(index1).set(index2, "X");
                            myBoard.get(0).set(index2, "*");
                        } else {
                            myBoard.get(index1).set(index2, myBoard.get(0).get(index2));
                            myBoard.get(0).set(index2, "*");
                        }
                    }
                }
            }
        }
        FileOutput.writeToFile("output.txt","\n\nYour output:\n",true,false);
        boolean ball = false;
        for (List<String> lines : myBoard) {
            if (lines.contains("*")) {
                ball = true;
            }
            for (String item : lines) {
                FileOutput.writeToFile("output.txt",item + " ",true,false);
            }
            FileOutput.writeToFile("output.txt","\n",true,false);
        }
        if (!ball) {
            FileOutput.writeToFile("output.txt","\nGame Over!",true,false);
        }
        FileOutput.writeToFile("output.txt","\nScore:" + points,true,false);
    }
}