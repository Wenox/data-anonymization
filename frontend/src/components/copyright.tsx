import {FC} from "react";
import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";

export const Copyright: FC = () => (
  <Typography
    variant="body2"
    color="text.secondary"
    sx={{ mt: 10, mb: 2 }}
  >
    {'Copyright © '}
    <Link color="inherit" href="about">
      Data Anonymisation Web Platform
    </Link>
    {' '}
    {new Date().getFullYear()}
    {'.'}
  </Typography>
);
