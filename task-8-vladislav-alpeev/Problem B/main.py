
def main():
  ioflow = IOFlow()
  text = get_input_params()
  textEntity = Text(text)
  solutionFinder = Solution(textEntity)
  processed_text = solutionFinder.find_the_solution()
  print_result(processed_text)

if __name__ == "__main__":
  main()